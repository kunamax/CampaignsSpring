import React, { useState, useEffect } from "react";
import axios from "axios";
import {
  TextField,
  Button,
  MenuItem,
  Select,
  InputLabel,
  FormControl,
  Grid,
  Typography,
  CircularProgress,
  Alert,
} from "@mui/material";
import KeywordsTypeahead from "./KeywordsTypeahead";
import TownTypeahead from "./TownTypeahead";

const AddCampaign = () => {
  const [campaignName, setCampaignName] = useState("");
  const [bidAmount, setBidAmount] = useState("");
  const [remainingBudget, setRemainingBudget] = useState("");
  const [status, setStatus] = useState(true);
  const [radius, setRadius] = useState("");
  const [productId, setProductId] = useState("");
  const [userId, setUserId] = useState("");

  const [selectedKeywords, setSelectedKeywords] = useState([]);
  const [towns, setTowns] = useState([]);
  const [selectedTown, setSelectedTown] = useState("");

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    const fetchTowns = async () => {
      try {
        const townsRes = await axios.get("http://localhost:8080/town/all");
        setTowns(townsRes.data);
      } catch (error) {
        console.error("Error fetching towns:", error);
        setError("Failed to load towns. Please try again later.");
      }
    };
    fetchTowns();
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError(null);
    setSuccess(false);

    if (!selectedTown) {
      setError("Please select a town");
      return;
    }

    if (selectedKeywords.length === 0) {
      setError("Please select or enter at least one keyword");
      return;
    }

    try {
      setLoading(true);

      const keywordNames = selectedKeywords.map((k) => k.keyWordName);
      const queryParams = new URLSearchParams({
        campaignName,
        status,
        bidAmount,
        remainingBudget,
        radius,
        town: selectedTown,
        productId,
        userId,
      });

      keywordNames.forEach((kw) => queryParams.append("keywords", kw));

      const url = `http://localhost:8080/campaigns/create?${queryParams.toString()}`;
      console.log("POST URL:", url);

      await axios.post(url);

      setSuccess(true);
      setCampaignName("");
      setSelectedKeywords([]);
      setBidAmount("");
      setRemainingBudget("");
      setRadius("");
      setProductId("");
      setUserId("");
      setSelectedTown("");
    } catch (error) {
      console.error("Error creating campaign:", error);
      setError(
        error.response?.data?.message ||
          "Failed to create campaign. Please try again.",
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 800, margin: "0 auto", padding: 20 }}>
      <Typography variant="h4" gutterBottom>
        Add New Campaign
      </Typography>

      {error && (
        <Alert severity="error" sx={{ mb: 3 }}>
          {error}
        </Alert>
      )}

      {success && (
        <Alert severity="success" sx={{ mb: 3 }}>
          Campaign created successfully! Refresh the page to see the changes.
        </Alert>
      )}

      <form onSubmit={handleSubmit}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <TextField
              label="Campaign Name"
              variant="outlined"
              fullWidth
              value={campaignName}
              onChange={(e) => setCampaignName(e.target.value)}
              required
            />
          </Grid>

          <Grid item xs={12}>
            <KeywordsTypeahead setKeywords={setSelectedKeywords} />
          </Grid>

          <Grid item xs={12} sm={6}>
            <TextField
              label="Bid Amount"
              variant="outlined"
              fullWidth
              type="number"
              value={bidAmount}
              onChange={(e) => setBidAmount(e.target.value)}
              required
              inputProps={{ min: 0, step: "0.01" }}
            />
          </Grid>

          <Grid item xs={12} sm={6}>
            <TextField
              label="Campaign Fund"
              variant="outlined"
              fullWidth
              type="number"
              value={remainingBudget}
              onChange={(e) => setRemainingBudget(e.target.value)}
              required
              inputProps={{ min: 0, step: "0.01" }}
            />
          </Grid>

          <Grid item xs={12}>
            <FormControl fullWidth required>
              <InputLabel>Status</InputLabel>
              <Select
                value={status}
                onChange={(e) => setStatus(e.target.value === "true")}
                disabled={loading}
              >
                <MenuItem value={"true"}>Active</MenuItem>
                <MenuItem value={"false"}>Inactive</MenuItem>
              </Select>
            </FormControl>
          </Grid>

          <Grid item xs={12}>
            <TownTypeahead setTown={setSelectedTown} />
          </Grid>

          <Grid item xs={12} sm={6}>
            <TextField
              label="Radius (km)"
              variant="outlined"
              fullWidth
              type="number"
              value={radius}
              onChange={(e) => setRadius(e.target.value)}
              required
              inputProps={{ min: 0 }}
            />
          </Grid>

          <Grid item xs={12} sm={6}>
            <TextField
              label="Product ID"
              variant="outlined"
              fullWidth
              type="number"
              value={productId}
              onChange={(e) => setProductId(e.target.value)}
              required
              inputProps={{ min: 1 }}
            />
          </Grid>

          <Grid item xs={12}>
            <TextField
              label="User ID"
              variant="outlined"
              fullWidth
              type="number"
              value={userId}
              onChange={(e) => setUserId(e.target.value)}
              required
              inputProps={{ min: 1 }}
            />
          </Grid>

          <Grid item xs={12}>
            <Button
              type="submit"
              variant="contained"
              color="primary"
              fullWidth
              size="large"
              disabled={loading}
            >
              {loading ? (
                <CircularProgress size={24} color="inherit" />
              ) : (
                "Create Campaign"
              )}
            </Button>
          </Grid>
        </Grid>
      </form>
    </div>
  );
};

export default AddCampaign;
