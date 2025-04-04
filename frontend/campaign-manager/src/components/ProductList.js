import React, { useState, useEffect } from "react";
import axios from "axios";
import {
  Box,
  Typography,
  CircularProgress,
  Alert,
  Paper,
  Grid,
  Card,
  CardContent,
} from "@mui/material";

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProducts = async () => {
      setLoading(true);
      try {
        const response = await axios.post("http://localhost:8080/products/all");
        setProducts(response.data);
        setError(null);
      } catch (err) {
        setError(err.response?.data || "Error fetching products");
        console.error("Product download error:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  return (
    <Box sx={{ p: 3, maxWidth: 1200, margin: "0 auto" }}>
      <Typography variant="h4" component="h2" gutterBottom>
        Product List
      </Typography>

      {loading && (
        <Box sx={{ display: "flex", justifyContent: "center", my: 4 }}>
          <CircularProgress />
        </Box>
      )}

      {error && (
        <Alert severity="error" sx={{ mb: 3 }}>
          {error.toString()}
        </Alert>
      )}

      {!loading &&
        !error &&
        (products.length > 0 ? (
          <Grid container spacing={3}>
            {products.map((product) => (
              <Grid item xs={12} sm={6} md={4} key={product.id}>
                <Card elevation={3}>
                  <CardContent>
                    <Typography variant="h6" component="h3" gutterBottom>
                      {product.productName}
                    </Typography>
                    <Typography
                      variant="body2"
                      color="text.secondary"
                      gutterBottom
                    >
                      ID: {product.id}
                    </Typography>
                    <Typography paragraph>
                      {product.productDescription || "No description available"}
                    </Typography>
                    <Typography variant="body1">
                      <strong>Created by:</strong> {product.userName}
                    </Typography>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
        ) : (
          <Paper sx={{ p: 3, textAlign: "center" }}>
            <Typography variant="body1">No products available</Typography>
          </Paper>
        ))}
    </Box>
  );
};

export default ProductList;
