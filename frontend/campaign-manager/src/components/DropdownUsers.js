import React, { useState, useEffect } from "react";
import axios from "axios";
import {
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  CircularProgress,
  Alert,
  Box,
  Typography,
  Chip,
  Avatar,
} from "@mui/material";
import PersonIcon from "@mui/icons-material/Person";
import AttachMoneyIcon from "@mui/icons-material/AttachMoney";

const DropdownUsers = ({ setSelectedUser }) => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [balance, setBalance] = useState(null);
  const [balanceLoading, setBalanceLoading] = useState(false);
  const [balanceError, setBalanceError] = useState(null);

  useEffect(() => {
    axios
      .get("http://localhost:8080/users/all")
      .then((response) => {
        setUsers(response.data);
        setError(null);
      })
      .catch((error) => {
        console.error("Błąd pobierania użytkowników:", error);
        setError("Nie udało się załadować listy użytkowników");
      })
      .finally(() => setLoading(false));
  }, []);

  const fetchUserBalance = async (userId) => {
    if (!userId) {
      setBalance(null);
      return;
    }

    setBalanceLoading(true);
    setBalanceError(null);
    try {
      const response = await axios.get(
        `http://localhost:8080/users/user_balance?userId=${userId}`,
      );
      // Konwersja na liczbę i formatowanie
      const balanceValue = Number(response.data);
      setBalance(isNaN(balanceValue) ? null : balanceValue);
    } catch (error) {
      console.error("Błąd pobierania stanu konta:", error);
      setBalanceError("Nie udało się załadować stanu konta");
    } finally {
      setBalanceLoading(false);
    }
  };

  const handleChange = (event) => {
    const userId = event.target.value;
    setSelectedUser(userId);
    fetchUserBalance(userId);
  };

  // Funkcja formatująca kwotę
  const formatBalance = (value) => {
    if (value === null || value === undefined) return "0.00";
    const num = Number(value);
    return isNaN(num) ? "0.00" : num.toFixed(2);
  };

  return (
    <Box sx={{ minWidth: 200, maxWidth: 400 }}>
      <FormControl fullWidth>
        <InputLabel id="user-select-label">
          <Box sx={{ display: "flex", alignItems: "center" }}>
            <PersonIcon sx={{ mr: 1 }} />
            Wybierz użytkownika
          </Box>
        </InputLabel>

        {loading ? (
          <Box sx={{ display: "flex", justifyContent: "center", p: 2 }}>
            <CircularProgress size={24} />
          </Box>
        ) : error ? (
          <Alert severity="error">{error}</Alert>
        ) : (
          <Select
            labelId="user-select-label"
            id="user-select"
            onChange={handleChange}
            label={
              <Box sx={{ display: "flex", alignItems: "center" }}>
                <PersonIcon sx={{ mr: 1 }} />
                Wybierz użytkownika
              </Box>
            }
            sx={{
              "& .MuiSelect-select": {
                display: "flex",
                alignItems: "center",
              },
            }}
          >
            <MenuItem value="">
              <em>Wybierz użytkownika</em>
            </MenuItem>
            {users.map((user) => (
              <MenuItem key={user.id} value={user.id}>
                <Box
                  sx={{ display: "flex", alignItems: "center", width: "100%" }}
                >
                  <Avatar sx={{ width: 24, height: 24, mr: 2 }}>
                    <PersonIcon fontSize="small" />
                  </Avatar>
                  <Typography>{user.username}</Typography>
                </Box>
              </MenuItem>
            ))}
          </Select>
        )}
      </FormControl>

      {balanceLoading && (
        <Box sx={{ display: "flex", justifyContent: "center", mt: 2 }}>
          <CircularProgress size={24} />
        </Box>
      )}

      {balanceError && (
        <Alert severity="error" sx={{ mt: 2 }}>
          {balanceError}
        </Alert>
      )}

      {balance !== null && !balanceLoading && (
        <Box sx={{ mt: 2 }}>
          <Chip
            icon={<AttachMoneyIcon />}
            label={`Stan konta: ${formatBalance(balance)} zł`}
            color="success"
            variant="outlined"
            sx={{
              fontSize: "1rem",
              padding: "8px 12px",
              ".MuiChip-icon": { color: "success.main" },
            }}
          />
        </Box>
      )}
    </Box>
  );
};

export default DropdownUsers;
