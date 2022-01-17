import React from 'react';
import './Header.css';
import {useHistory, useLocation} from "react-router-dom";
import {Pages} from "../Utils/Enums";
import Avatar from '@mui/material/Avatar';
import {Divider, ListItemIcon, Menu, MenuItem, Stack} from "@mui/material";
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import {AccountCircle, Logout, Settings, Login} from "@mui/icons-material";
import Logo from '../Resources/timetable.png';
import {getUser} from "../Utils/Token";

export const Header = (props) => {
    let history = useHistory();
    const location = useLocation();
    const [anchorEl, setAnchorEl] = React.useState(null);
    const open = Boolean(anchorEl);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const onLogout = () => {
        props.setToken(null, null);
        handleClose();
        history.push(Pages.HOME);
    }

    return (
        <header>
            <Stack className="navmenu" direction="row" spacing="50px">
                <Stack direction="row">
                    <div className="logo">
                        <img src={Logo} alt="logo" height="40px" width="40px"/>
                    </div>
                    <div className="title" onClick={() => {
                        history.push(Pages.HOME);
                    }}>
                        <span className='orange'>DEAD</span>LIИE
                    </div>
                </Stack>
                <div className={location.pathname === Pages.BOARD ? "selected" : "link"} onClick={() => {
                    history.push(Pages.BOARD);
                }}>
                    Boards
                </div>
                <div className={location.pathname === Pages.BACKLOG ? "selected" : "link"} onClick={() => {
                    history.push(Pages.BACKLOG);
                }}>
                    Backlog
                </div>
                <div className={location.pathname === Pages.COMMUNITY ? "selected" : "link"} onClick={() => {
                    history.push(Pages.COMMUNITY);
                }}>
                    Community
                </div>
            </Stack>


            <Stack direction="row" className="right"
                   onClick={handleClick}>
                <div className="avatar">
                    <Avatar alt="User"
                            src={props.token && getUser().profilePictureURL ? '/' + getUser().profilePictureURL : "https://www.pngkey.com/png/full/230-2301779_best-classified-apps-default-user-profile.png"}
                            sx={{width: 30, height: 30}}/>
                </div>
                <div className="dropdown-icon">
                    <ArrowDropDownIcon htmlColor="white"/>
                </div>
            </Stack>


            <Menu anchorEl={anchorEl}
                  open={open}
                  onClose={handleClose}
                  className="dropdown"
                  anchorOrigin={{vertical: 'center', horizontal: 'right'}}
                  transformOrigin={{vertical: 'top', horizontal: 'center'}}>
                {props.token ? [
                        <MenuItem className="dropdown-logged-user">
                            <p>Logged in as</p>
                            <p className="name">{getUser().username}</p>
                        </MenuItem>,
                        <Divider/>,
                        <MenuItem className="dropdown-item"
                                  onClick={() => {
                                      handleClose();
                                      history.push(Pages.BOARD);
                                  }}>
                            <ListItemIcon>
                                <AccountCircle fontSize="small" htmlColor="#9D9D9D" className="icon"/>
                            </ListItemIcon>
                            Profile
                        </MenuItem>,
                        <MenuItem className="dropdown-item"
                                  onClick={() => {
                                      handleClose();
                                      history.push(Pages.BOARD);
                                  }}>
                            <ListItemIcon>
                                <Settings fontSize="small" htmlColor="#9D9D9D" className="icon"/>
                            </ListItemIcon>
                            Settings
                        </MenuItem>,
                        <MenuItem className="logout"
                                  onClick={onLogout}>
                            <ListItemIcon>
                                <Logout fontSize="small" htmlColor="#9D9D9D" className="logout-icon"/>
                            </ListItemIcon>
                            Logout
                        </MenuItem>
                    ]
                    :
                    <MenuItem className="dropdown-item"
                              onClick={() => {
                                  handleClose();
                                  history.push(Pages.HOME);
                              }}
                              sx={{width: '120px'}}>
                        <ListItemIcon>
                            <Login fontSize="small" htmlColor="#9D9D9D" className="icon"/>
                        </ListItemIcon>
                        Login
                    </MenuItem>}
            </Menu>
        </header>
    );
}