import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  TextField,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Grid,
  Typography,
  InputAdornment,
  Alert,
} from "@mui/material";
import KeywordsTypeahead from "./KeywordsTypeahead"; // ten sam komponent co w AddCampaign.js

const EditCampaign = () => {
  const [campaignId, setCampaignId] = useState("");
  const [campaignName, setCampaignName] = useState("");
  const [status, setStatus] = useState(true);
  const [bidAmount, setBidAmount] = useState("");
  const [remainingBudget, setRemainingBudget] = useState("");
  const [radius, setRadius] = useState("");

  const [selectedKeywords, setSelectedKeywords] = useState([]);
  const [towns, setTowns] = useState([]);
  const [selectedTown, setSelectedTown] = useState("");
  const [newTown, setNewTown] = useState("");

  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    axios
      .get("http://localhost:8080/town/all")
      .then((res) => setTowns(res.data))
      .catch((err) => console.error("❌ Error loading towns:", err));
  }, []);

  const handleUpdate = async () => {
    setError(null);
    setSuccess(false);

    if (!campaignId) {
      setError("Enter Campaign ID");
      return;
    }

    const townToSend = newTown.trim() !== "" ? newTown : selectedTown;
    if (!townToSend) {
      setError("Select or add a city");
      return;
    }

    if (selectedKeywords.length === 0) {
      setError("Select or add keywords");
      return;
    }

    try {
      const keywordNames = selectedKeywords.map((kw) => kw.keyWordName);
      const params = new URLSearchParams({
        campaignName,
        status,
        bidAmount,
        remainingBudget,
        radius,
        town: townToSend,
      });
      keywordNames.forEach((kw) => params.append("keywords", kw));

      await axios.put(
        `http://localhost:8080/campaigns/update?campaignId=${campaignId}&${params.toString()}`,
      );

      setSuccess(true);
    } catch (err) {
      console.error(err);
      setError("❌ Failed to update campaign");
    }
  };

  return (
    <div style={{ maxWidth: 600, margin: "0 auto", padding: 20 }}>
      <Typography variant="h4" gutterBottom>
        Edit Campaign
      </Typography>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}
      {success && (
        <Alert severity="success" sx={{ mb: 2 }}>
          ✅ Campaign Updated!
        </Alert>
      )}

      <Grid container spacing={3}>
        <Grid item xs={12}>
          <TextField
            label="Campaign ID"
            variant="outlined"
            fullWidth
            type="number"
            value={campaignId}
            onChange={(e) => setCampaignId(e.target.value)}
            required
          />
        </Grid>

        <Grid item xs={12}>
          <TextField
            label="Name of Campaign"
            variant="outlined"
            fullWidth
            value={campaignName}
            onChange={(e) => setCampaignName(e.target.value)}
          />
        </Grid>

        <Grid item xs={12}>
          <FormControl fullWidth>
            <InputLabel>Status</InputLabel>
            <Select
              value={status}
              label="Status"
              onChange={(e) => setStatus(e.target.value === "true")}
            >
              <MenuItem value={"true"}>Aktywna</MenuItem>
              <MenuItem value={"false"}>Nieaktywna</MenuItem>
            </Select>
          </FormControl>
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField
            label="Bid Amount (PLN)"
            type="number"
            fullWidth
            value={bidAmount}
            onChange={(e) => setBidAmount(e.target.value)}
          />
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField
            label="Campaign fund (PLN)"
            type="number"
            fullWidth
            value={remainingBudget}
            onChange={(e) => setRemainingBudget(e.target.value)}
          />
        </Grid>

        <Grid item xs={12}>
          <TextField
            label="Radius (km)"
            type="number"
            fullWidth
            value={radius}
            onChange={(e) => setRadius(e.target.value)}
          />
        </Grid>

        <Grid item xs={12}>
          <KeywordsTypeahead setKeywords={setSelectedKeywords} />
        </Grid>

        <Grid item xs={12}>
          <FormControl fullWidth>
            <InputLabel>Miasto</InputLabel>
            <Select
              value={selectedTown}
              onChange={(e) => setSelectedTown(e.target.value)}
              disabled={newTown !== ""}
            >
              <MenuItem value="">
                <em>Choose City</em>
              </MenuItem>
              {towns.map((town) => (
                <MenuItem key={town.id} value={town.townName}>
                  {town.townName}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        <Grid item xs={12}>
          <TextField
            label="Add New Town(optional)"
            variant="outlined"
            fullWidth
            value={newTown}
            onChange={(e) => {
              setNewTown(e.target.value);
              if (e.target.value.trim() !== "") {
                setSelectedTown("");
              }
            }}
          />
        </Grid>

        <Grid item xs={12}>
          <Button
            onClick={handleUpdate}
            variant="contained"
            color="primary"
            fullWidth
            size="large"
          >
            Save Changes
          </Button>
        </Grid>
      </Grid>
    </div>
  );
};

export default EditCampaign;
