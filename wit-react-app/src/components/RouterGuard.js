import {useEffect, useState} from "react";
import React from "react"
const RouterGuard = ({children})=>{
   const [loggedIn, setLoggedIn]= useState(false)
    useEffect(
        ()=>{
            if(JSON.parse(localStorage.getItem('user'))){
                setLoggedIn(true);
            }

        }
    ,[setLoggedIn])


    return(
        loggedIn? (children):<div>Not Logged In</div>
    )
}

export default RouterGuard;