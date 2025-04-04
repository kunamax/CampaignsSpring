import React, { useState } from "react";
import axios from "axios";
import { TextField, Button, Checkbox, FormControlLabel } from "@mui/material";
import KeywordsTypeahead from "./KeywordsTypeahead";
import DropdownTown from "./DropdownTown";

const CampaignForm = ({ addCampaign }) => {
  const [campaignName, setCampaignName] = useState("");
  const [keywords, setKeywords] = useState([]);
  const [bidAmount, setBidAmount] = useState("");
  const [remainingBudget, setRemainingBudget] = useState("");
  const [status, setStatus] = useState(false);
  const [town, setTown] = useState("");
  const [radius, setRadius] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    const campaignData = {
      campaignName,
      keywords,
      bidAmount,
      remainingBudget,
      status,
      town,
      radius,
      userId: 1,
    };

    axios
      .post("http://localhost:8080/campaigns/create", campaignData)
      .then((response) => {
        addCampaign(response.data);
        alert("Campaign created successfully!");
      })
      .catch((error) =>
        console.error("There was an error creating the campaign:", error),
      );
  };

  return (
    <form onSubmit={handleSubmit}>
      <TextField
        label="Campaign Name"
        value={campaignName}
        onChange={(e) => setCampaignName(e.target.value)}
        required
      />
      <KeywordsTypeahead setKeywords={setKeywords} />
      <TextField
        label="Bid Amount"
        type="number"
        value={bidAmount}
        onChange={(e) => setBidAmount(e.target.value)}
        required
      />
      <TextField
        label="Remaining Budget"
        type="number"
        value={remainingBudget}
        onChange={(e) => setRemainingBudget(e.target.value)}
        required
      />
      <FormControlLabel
        control={
          <Checkbox
            checked={status}
            onChange={(e) => setStatus(e.target.checked)}
          />
        }
        label="Status (On/Off)"
      />
      <DropdownTown setTown={setTown} />
      <TextField
        label="Radius (km)"
        type="number"
        value={radius}
        onChange={(e) => setRadius(e.target.value)}
        required
      />
      <Button type="submit">Create Campaign</Button>
    </form>
  );
};

export default CampaignForm;
