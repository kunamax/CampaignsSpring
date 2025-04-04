import React, { useState, useEffect } from "react";
import axios from "axios";

const CampaignList = ({ userId }) => {
  const [campaigns, setCampaigns] = useState([]);

  useEffect(() => {
    if (userId) {
      axios
        .get(`http://localhost:8080/campaigns/user_campaigns?userId=${userId}`)
        .then((response) => {
          setCampaigns(response.data);
        })
        .catch((error) => console.error("Campaign download error:", error));
    }
  }, [userId]);

  return (
    <div>
      <h2>Campaigns List</h2>
      {userId ? (
        campaigns.length > 0 ? (
          <ul>
            {campaigns.map((campaign) => (
              <li key={campaign.id} style={{ marginBottom: "25px" }}>
                <strong>{campaign.campaignName}</strong> (ID: {campaign.id})
                <br />
                Status: {campaign.status ? "Active" : "Not Active"}
                <br />
                Bid amount: {campaign.bidAmount} zł
                <br />
                Campaign fund: {campaign.remainingBudget} zł
                <br />
                Date of creation: {campaign.createdAt}
                <br />
                Radius: {campaign.radius} km
                <br />
                Product: {campaign.productName}
                <br />
                User: {campaign.userName}
                <br />
                Town: {campaign.town}
                <br />
                Key Words:{" "}
                {campaign.keyWords && campaign.keyWords.length > 0
                  ? campaign.keyWords.join(", ")
                  : "None"}
              </li>
            ))}
          </ul>
        ) : (
          <p>No campaigns for this user</p>
        )
      ) : (
        <p>Select user</p>
      )}
    </div>
  );
};

export default CampaignList;
