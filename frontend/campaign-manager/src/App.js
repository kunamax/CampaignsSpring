import React, { useState } from "react";
import Navbar from "./components/Navbar";
import CampaignList from "./components/CampaignList";
import AddCampaign from "./components/AddCampaign";
import EditCampaign from "./components/EditCampaign";
import DeleteCampaign from "./components/DeleteCampaign";
import DropdownUsers from "./components/DropdownUsers";
import CreateUser from "./components/CreateUser";
import CreateProduct from "./components/CreateProduct";
import ProductList from "./components/ProductList";

function App() {
  const [view, setView] = useState("list");
  const [selectedUser, setSelectedUser] = useState(null);

  return (
    <div>
      <Navbar setView={setView} />
      <DropdownUsers setSelectedUser={setSelectedUser} />
      {view === "list" && <CampaignList userId={selectedUser} />}
      {view === "add" && <AddCampaign />}
      {view === "edit" && <EditCampaign />}
      {view === "delete" && <DeleteCampaign />}
      {view === "create user" && <CreateUser />}
      {view === "create product" && <CreateProduct />}
      {view === "productList" && <ProductList />}
    </div>
  );
}

export default App;
