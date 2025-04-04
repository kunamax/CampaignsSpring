import React, { useState } from "react";
import {
  TextField,
  Button,
  Typography,
  Grid,
  Alert,
  CircularProgress,
} from "@mui/material";
import axios from "axios";

const CreateUser = () => {
  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
    phoneNumber: "",
    balance: "",
    countryISO2Code: "",
  });
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleCreateUser = async () => {
    setSuccess(false);
    setError(null);
    setLoading(true);

    try {
      const params = new URLSearchParams();

      Object.entries(form).forEach(([key, value]) => {
        if (value !== "") {
          params.append(key, value);
        }
      });

      await axios.post(
        `http://localhost:8080/users/create?${params.toString()}`,
      );

      setSuccess(true);
      setForm({
        name: "",
        email: "",
        password: "",
        phoneNumber: "",
        balance: "",
        countryISO2Code: "",
      });
    } catch (err) {
      const errorMessage =
        err.response?.data?.message ||
        err.response?.data?.error ||
        (typeof err.response?.data === "string"
          ? err.response.data
          : "An error occurred while creating a user");
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 600, margin: "0 auto", padding: 20 }}>
      <Typography variant="h4" gutterBottom>
        Add New User
      </Typography>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}
      {success && (
        <Alert severity="success" sx={{ mb: 2 }}>
          User Added!
        </Alert>
      )}

      <Grid container spacing={2}>
        {[
          { field: "name", label: "Fullname", type: "text" },
          { field: "email", label: "Email", type: "email" },
          { field: "password", label: "Password", type: "password" },
          { field: "phoneNumber", label: "Mobile Phone", type: "tel" },
          { field: "balance", label: "Balance", type: "number" },
          {
            field: "countryISO2Code",
            label: "Country ISO2CODE, e.g. PL",
            type: "text",
          },
        ].map(({ field, label, type }) => (
          <Grid item xs={12} key={field}>
            <TextField
              label={label}
              name={field}
              fullWidth
              type={type}
              value={form[field]}
              onChange={handleChange}
              required
              inputProps={field === "balance" ? { step: "0.01" } : {}}
            />
          </Grid>
        ))}

        <Grid item xs={12}>
          <Button
            variant="contained"
            fullWidth
            onClick={handleCreateUser}
            disabled={loading}
            startIcon={
              loading && <CircularProgress size={20} color="inherit" />
            }
          >
            {loading ? "Creating..." : "Create User"}
          </Button>
        </Grid>
      </Grid>
    </div>
  );
};

export default CreateUser;
