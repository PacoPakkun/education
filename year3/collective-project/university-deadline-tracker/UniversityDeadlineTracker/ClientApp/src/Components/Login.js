import React, {useRef, useState} from "react";
import "./Login.css";
import {addUser, login} from "../Utils/Services";
import {
    Button, CircularProgress,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    FormControl,
    Input,
    InputAdornment,
    InputLabel,
    Stack,
    TextField,
} from "@mui/material";
import {AccountCircle, Visibility, VisibilityOff} from "@mui/icons-material";

const getGenericUser = () => {
    return {
        username: '',
        password: '',
        firstName: '',
        lastName: '',
        email: '',
        group: '',
        year: '',
        code: '',
        profilePictureUrl: '',
        dateOfBirth: ''
    }
}

const isUserValid = (user) => {
    return user.username.length > 0 &&
        user.password.length > 0 &&
        user.firstName.length > 0 &&
        user.lastName.length > 0 &&
        user.email.length > 0 &&
        user.group.length > 0 &&
        user.year.length > 0 &&
        user.code.length > 0 &&
        user.profilePictureUrl.length > 0 &&
        user.dateOfBirth.length > 0 &&
        !isNaN(user.group) &&
        !isNaN(user.year) &&
        !isNaN(user.code)
}

export const Login = (props) => {
    const [user, setUser] = useState(getGenericUser);
    const [showPassword, setShowPassword] = useState(false);
    const [loginError, setLoginError] = useState(false);
    const [shouldSignUp, setShouldSignUp] = useState(false);
    const [isSigningUp, setIsSigningUp] = useState(false);
    const [signupError, setSignupError] = useState(false);
    const inputFile = useRef(null);

    const onLogin = () => {
        login(user.username, user.password).then((data) => {
            if (data) {
                props.setToken(data.token, data.user);
            } else setLoginError(true);
        });
    };

    const onSignup = () => {
        setIsSigningUp(true)
        addUser({
            ...user,
            group: Number.parseInt(user.group),
            year: Number.parseInt(user.year),
            code: Number.parseInt(user.code),
            role: 1,
            subjects: [],
        }).then((response) => {
            if (response.status === 200) onLogin();
            else setSignupError(true);
        });
    }

    const getSignupFields = () => {
        const genericProps = {required: true, variant: 'standard', className: 'textfield'}
        return (
            <div>
                <Stack direction="row" spacing={10}>
                    <TextField
                        label="Username"
                        type="text"
                        autofocus
                        {...genericProps}
                        onChange={(event) => {
                            setUser({...user, username: event.target.value,});
                        }}
                    />
                    <TextField
                        label="Password"
                        type="password"
                        {...genericProps}
                        onChange={(event) => {
                            setUser({...user, password: event.target.value,});
                        }}
                    />
                </Stack>
                <Stack direction="row" spacing={10}>
                    <TextField
                        label="First Name"
                        type="text"
                        {...genericProps}
                        onChange={(event) => {
                            setUser({...user, firstName: event.target.value,});
                        }}
                    />
                    <TextField
                        label="Last Name"
                        type="text"
                        {...genericProps}
                        onChange={(event) => {
                            setUser({...user, lastName: event.target.value,});
                        }}
                    />
                </Stack>
                <Stack direction="row" spacing={10}>
                    <TextField
                        label="Email Address"
                        type="email"
                        {...genericProps}
                        onChange={(event) => {
                            setUser({...user, email: event.target.value,});
                        }}
                    />
                    <TextField
                        label="Group"
                        type="text"
                        error={user.group && isNaN(user.group)}
                        helperText="Please enter a number."
                        {...genericProps}
                        onChange={(event) => {
                            setUser({...user, group: event.target.value,});
                        }}
                    />
                </Stack>
                <Stack direction="row" spacing={10}>
                    <TextField
                        label="Year"
                        type="text"
                        error={user.year && isNaN(user.year)}
                        helperText="Please enter a number."
                        {...genericProps}
                        onChange={(event) => {
                            setUser({...user, year: event.target.value,});
                        }}
                    />
                    <TextField
                        label="Code"
                        type="text"
                        error={user.code && isNaN(user.code)}
                        helperText="Please enter a number."
                        {...genericProps}
                        onChange={(event) => {
                            setUser({...user, code: event.target.value,});
                        }}
                    />
                </Stack>
                <Stack direction="row" spacing={10}>
                    <TextField
                        label="Profile Picture"
                        type="text"
                        {...genericProps}
                        value={user.profilePictureUrl}
                        onClick={() => inputFile.current.click()}
                    />
                    <input
                        type="file"
                        id="file"
                        ref={inputFile}
                        style={{display: "none"}}
                        onChange={onChangeFile.bind(this)}
                    />
                    <TextField
                        label="Birthday"
                        type="date"
                        {...genericProps}
                        InputLabelProps={{shrink: true,}}
                        onChange={(event) => {
                            setUser({...user, dateOfBirth: event.target.value,});
                        }}
                    />
                </Stack>
            </div>
        )
    }

    const onChangeFile = (event) => {
        event.stopPropagation();
        event.preventDefault();
        const file = event.target.files[0];
        setUser({...user, profilePictureUrl: file.name});
    };

    return (
        <div className="login-component">
            <form className="form">
                <FormControl variant="standard" className="input">
                    <InputLabel>Username</InputLabel>
                    <Input type="text"
                           endAdornment={
                               <InputAdornment position="end">
                                   <AccountCircle/>
                               </InputAdornment>
                           }
                           onChange={(event) => {
                               setUser({...user, username: event.target.value});
                           }}
                    />
                </FormControl>
                <FormControl variant="standard" className="input">
                    <InputLabel>Password</InputLabel>
                    <Input
                        type={showPassword ? "text" : "password"}
                        endAdornment={
                            <InputAdornment
                                position="end"
                                style={{cursor: "pointer"}}>
                                {showPassword ?
                                    <VisibilityOff onClick={() =>
                                        showPassword ? setShowPassword(false) : setShowPassword(true)
                                    }/>
                                    :
                                    <Visibility onClick={() =>
                                        showPassword ? setShowPassword(false) : setShowPassword(true)
                                    }/>
                                }
                            </InputAdornment>
                        }
                        onChange={(event) => {
                            setUser({...user, password: event.target.value});
                        }}
                    />
                </FormControl>
                {loginError && (
                    <span>Invalid username or password. Please try again!</span>
                )}
                <Stack direction="row">
                    <Button type="button"
                            color="inherit"
                            className="button"
                            onClick={onLogin}>
                        Login
                    </Button>
                    <Button type="button"
                            color="inherit"
                            className="button"
                            onClick={() => {
                                setShouldSignUp(true);
                            }}>
                        Sign up
                    </Button>
                </Stack>


                <Dialog open={shouldSignUp}
                        onClose={() => {
                            setShouldSignUp(false);
                        }}>
                    <Dialog className="login-component-spinner-dialog" open={isSigningUp} color="error">
                        <CircularProgress color="warning"/>
                    </Dialog>
                    <DialogTitle>Create Account</DialogTitle>
                    <DialogContent>
                        <DialogContentText>
                            Please fill in your personal information.
                        </DialogContentText>
                        {signupError && <DialogContentText color='error'>
                            Something went wrong. Please try again!
                        </DialogContentText>}
                        {getSignupFields()}
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={() => {
                            setShouldSignUp(false);
                        }}>
                            Cancel
                        </Button>
                        <Button onClick={onSignup} disabled={!isUserValid(user)}>
                            Sign Up
                        </Button>
                    </DialogActions>
                </Dialog>
            </form>
        </div>
    );
};
