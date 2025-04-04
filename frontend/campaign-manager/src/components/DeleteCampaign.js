import React, { useState } from "react";
import axios from "axios";
import {
  TextField,
  Button,
  Typography,
  Grid,
  Alert,
  Container,
} from "@mui/material";

const DeleteCampaign = () => {
  const [campaignId, setCampaignId] = useState("");
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState(null);

  const handleDelete = () => {
    setSuccess(false);
    setError(null);

    if (!campaignId) {
      setError("Enter campaign ID");
      return;
    }

    axios
      .delete(`http://localhost:8080/campaigns/delete?campaignId=${campaignId}`)
      .then(() => {
        setSuccess(true);
        setCampaignId("");
      })
      .catch((err) => {
        console.error("Campaign deletion error:", err);
        setError("The campaign could not be removed.");
      });
  };

  return (
    <Container maxWidth="sm" sx={{ mt: 4 }}>
      <Typography variant="h4" gutterBottom>
        Delete Campaign
      </Typography>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}
      {success && (
        <Alert severity="success" sx={{ mb: 2 }}>
          Campaign Deleted!
        </Alert>
      )}

      <Grid container spacing={3}>
        <Grid item xs={12}>
          <TextField
            label="Campaign ID"
            type="number"
            fullWidth
            variant="outlined"
            value={campaignId}
            onChange={(e) => setCampaignId(e.target.value)}
          />
        </Grid>
        <Grid item xs={12}>
          <Button
            onClick={handleDelete}
            variant="contained"
            color="error"
            fullWidth
          >
            Delete Campaign
          </Button>
        </Grid>
      </Grid>
    </Container>
  );
};

export default DeleteCampaign;
