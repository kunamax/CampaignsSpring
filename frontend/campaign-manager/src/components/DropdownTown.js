import { useEffect, useState } from "react";
import axios from "axios";

const DropdownTown = () => {
  const [towns, setTowns] = useState([]);
  const [selectedTown, setSelectedTown] = useState("");

  useEffect(() => {
    axios
      .get("http://localhost:8080/town/all")
      .then((response) => {
        console.log("✅ API response:", response.data); // Sprawdzenie danych
        setTowns(response.data);
        if (response.data.length > 0) {
          setSelectedTown(response.data[0].id); // Wybiera pierwsze miasto jako domyślne
        }
      })
      .catch((error) => console.error("❌ Error fetching towns:", error));
  }, []);

  return (
    <div>
      <label>Wybierz miasto:</label>
      <select
        value={selectedTown}
        onChange={(e) => setSelectedTown(e.target.value)}
        disabled={towns.length === 0}
      >
        {towns.length === 0 ? (
          <option>Brak dostępnych miast</option>
        ) : (
          towns.map((town) => (
            <option key={town.id} value={town.id}>
              {town.townName}
            </option>
          ))
        )}
      </select>
    </div>
  );
};

export default DropdownTown;
