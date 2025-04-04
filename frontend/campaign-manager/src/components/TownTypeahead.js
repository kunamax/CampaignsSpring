import { useEffect, useState } from "react";
import axios from "axios";
import { Autocomplete, TextField } from "@mui/material";

const TownTypeahead = ({ setTown }) => {
  const [townOptions, setTownOptions] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/town/all")
      .then((response) => {
        const names = response.data.map((t) => t.townName);
        setTownOptions(names);
      })
      .catch((error) => console.error("❌ Error fetching towns:", error));
  }, []);

  return (
    <Autocomplete
      freeSolo
      options={townOptions}
      onChange={(event, value) => setTown(value)}
      onInputChange={(event, value) => setTown(value)}
      renderInput={(params) => (
        <TextField {...params} label="Town" variant="outlined" fullWidth />
      )}
    />
  );
};

export default TownTypeahead;
