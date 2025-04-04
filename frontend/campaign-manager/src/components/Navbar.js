import React from "react";
import { AppBar, Toolbar, Button, Box, Typography } from "@mui/material";
import CampaignIcon from "@mui/icons-material/Campaign";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import ShoppingBasketIcon from "@mui/icons-material/ShoppingBasket";
import ListIcon from "@mui/icons-material/List";

const Navbar = ({ setView }) => {
  const navItems = [
    { label: "List of campaigns", view: "list", icon: <CampaignIcon /> },
    { label: "Add campaign", view: "add", icon: <AddCircleIcon /> },
    { label: "Edit campaign", view: "edit", icon: <EditIcon /> },
    { label: "Delete campaign", view: "delete", icon: <DeleteIcon /> },
    { label: "Add user", view: "create user", icon: <PersonAddIcon /> },
    {
      label: "Add product",
      view: "create product",
      icon: <ShoppingBasketIcon />,
    },
    { label: "Product list", view: "productList", icon: <ListIcon /> },
  ];

  return (
    <AppBar position="static" sx={{ mb: 4 }}>
      <Toolbar>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          Campaigns Manager
        </Typography>
        <Box sx={{ display: { xs: "none", sm: "block" } }}>
          {navItems.map((item) => (
            <Button
              key={item.view}
              color="inherit"
              startIcon={item.icon}
              onClick={() => setView(item.view)}
              sx={{
                mx: 1,
                "&:hover": {
                  backgroundColor: "rgba(255, 255, 255, 0.1)",
                },
              }}
            >
              {item.label}
            </Button>
          ))}
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
