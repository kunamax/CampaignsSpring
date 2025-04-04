import React, { useState, useEffect } from "react";
import {
  Autocomplete,
  TextField,
  CircularProgress,
  Alert,
} from "@mui/material";
import axios from "axios";

const KeywordsTypeahead = ({ setKeywords }) => {
  const [options, setOptions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    axios
      .get("http://localhost:8080/keyword/all")
      .then((response) => {
        setOptions(response.data);
        setError(null);
      })
      .catch((error) => {
        console.error("Error fetching keywords:", error);
        setError("Failed to load keywords");
      })
      .finally(() => setLoading(false));
  }, []);

  return (
    <div>
      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}
      <Autocomplete
        multiple
        freeSolo
        options={options.map((option) => option.keyWordName)} // używamy tylko nazw
        onChange={(event, newValue) => {
          const selected = newValue.map((val) => {
            const existing = options.find((k) => k.keyWordName === val);
            return existing || { keyWordName: val }; // nowy jeśli nie istnieje
          });
          setKeywords(selected);
        }}
        renderInput={(params) => (
          <TextField
            {...params}
            label="Keywords"
            InputProps={{
              ...params.InputProps,
              endAdornment: (
                <>
                  {loading ? (
                    <CircularProgress color="inherit" size={20} />
                  ) : null}
                  {params.InputProps.endAdornment}
                </>
              ),
            }}
          />
        )}
      />
    </div>
  );
};

export default KeywordsTypeahead;
