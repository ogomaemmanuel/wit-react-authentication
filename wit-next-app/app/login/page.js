'use client'
import React, {useState} from 'react';
import {signIn} from "next-auth/react";

const LoginForm = () => {
    const [user, setUser] = useState(
        {
            "email": "",
            "password": "",
        }
    )

    const login = async () => {
       // await signIn("azure-ad")
        await signIn("github",{callbackUrl:"/blogs"})
    }

    const onInput= (event)=>{
        setUser(prev=>{
           return  {...prev,...{[event.target.name]:event.target.value}}
        })
    }

    const credentialsLogin = async () => {
        await signIn("credentials", {
            email: user.email,
            password: user.password
        })
    }
    return (
        <div>

                <div className="field">
                    <label className="label">Name</label>
                    <div className="control">
                        <input
                            onInput={onInput}
                            className="input" name="email" type="text" placeholder="Text input"/>
                    </div>
                </div>
                <div className="field">
                    <label className="label">Name</label>
                    <div className="control">
                        <input className="input"
                               onInput={onInput}
                               name="password" type="text" placeholder="Text input"/>
                    </div>
                </div>

                <div className="field is-grouped">
                    <div className="control">
                        <button
                            onClick={credentialsLogin}
                            className="button is-link">Login</button>
                    </div>
                </div>
                <div className="field is-grouped">
                    <div className="control">
                        <button onClick={login} className="button is-link">Login With AD</button>
                    </div>
                </div>
        </div>
    )
}

export default LoginForm;