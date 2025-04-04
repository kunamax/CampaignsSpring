import React, { useState } from "react";
import { TextField, Button, Typography, Grid, Alert } from "@mui/material";
import axios from "axios";

const CreateProduct = () => {
  const [form, setForm] = useState({
    name: "",
    description: "",
    userId: "",
  });
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleCreateProduct = async () => {
    setSuccess(false);
    setError(null);
    try {
      const params = new URLSearchParams(form);
      await axios.post(
        `http://localhost:8080/products/create?${params.toString()}`,
      );
      setSuccess(true);
      setForm({ name: "", description: "", userId: "" });
    } catch (err) {
      const errorMessage =
        err.response?.data?.message ||
        err.response?.data?.error ||
        err.message ||
        "❌ Błąd przy tworzeniu produktu";

      setError(errorMessage);
    }
  };

  return (
    <div style={{ maxWidth: 600, margin: "0 auto", padding: 20 }}>
      <Typography variant="h4" gutterBottom>
        Add New Product
      </Typography>

      {error && <Alert severity="error">{error}</Alert>}
      {success && <Alert severity="success">Product Added!</Alert>}

      <Grid container spacing={2}>
        {["name", "description", "userId"].map((field) => (
          <Grid item xs={12} key={field}>
            <TextField
              label={field.charAt(0).toUpperCase() + field.slice(1)}
              name={field}
              fullWidth
              type={field === "userId" ? "number" : "text"}
              value={form[field]}
              onChange={handleChange}
              required
            />
          </Grid>
        ))}

        <Grid item xs={12}>
          <Button
            variant="contained"
            fullWidth
            onClick={handleCreateProduct}
            disabled={!form.name || !form.description || !form.userId}
          >
            Create Product
          </Button>
        </Grid>
      </Grid>
    </div>
  );
};

export default CreateProduct;
