import React, {useState} from 'react'
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import axios from "axios";


const LoginPage = (props) => {
    const navigate = useNavigate();
    const [user, setUser] = useState({
        "username": "",
        "password": "",
    })

    const onInputHandler=(event)=>{
        setUser(
            prev => {
                return {...prev, ...{[event.target.name]: event.target.value}}
            }
        )
    }

  const   login= async (event)=>{
      event.preventDefault();
      try {


          const resp = await axios.post(`/api/v1/auth/login`, user)
          const responseData = await resp.data;
          localStorage.setItem("user", JSON.stringify(responseData));
          navigate("/blogs");
          console.log(responseData);
      }catch(error){
          console.log("Login Error",error);
      }
  }
    return (
        <div>
            <form onSubmit={login}>
            <div className="field">
                <label className="label">Emaiil</label>
                <div className="control">
                    <input onInput={onInputHandler} name={"username"} className="input" type="text" placeholder="Text input"/>
                </div>
            </div>

            <div className="field">
                <label className="label">Password</label>
                <div className="control">
                    <input onInput={onInputHandler} name={"password"} className="input" type="text" placeholder="Text input"/>
                </div>
            </div>

            <div className="field ">
                <div className="control is-expanded">
                    <button className="button is-link is-fullwidth">Login</button>
                </div>
            </div>

                <div className="field ">
                    <div className="control is-expanded">
                        <Link className="button is-link" to="/register">Register</Link>
                    </div>
                </div>
            </form>
       </div>
    )
}

export default LoginPage;