import React, {useState} from "react";

const UserRegistrationPage = () => {
    const [user, setUser] = useState({
        "firstName": "",
        "lastName": "",
        "email": "",
        "password": "",
    })

    const register = async (event) => {
        event.preventDefault();
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        };
        const resp = await fetch(`${process.env.REACT_APP_API_ENDPOINT_URL}/api/v1/blogs`, options)
        const responseData = await resp.json();
        console.log(responseData);
    }

    const onInputHandler = (event) => {
        setUser(prev => {
            return {...prev, ...{[event.target.name]: event.target.value}}
        })
    }

    return (
        <div>
            <form onSubmit={register}>
                <div className="field">
                    <label className="label">First Name</label>
                    <div className="control">
                        <input onInput={onInputHandler} name={"firstName"} className="input" type="text"
                               placeholder="Text input"/>
                    </div>
                </div>

                <div className="field">
                    <label className="label">Last Name</label>
                    <div className="control">
                        <input onInput={onInputHandler} name={"lastName"} className="input" type="text"
                               placeholder="Text input"/>
                    </div>
                </div>

                <div className="field">
                    <label className="label">Email</label>
                    <div className="control">
                        <input onInput={onInputHandler} name={"email"} className="input" type="text"
                               placeholder="Text input"/>
                    </div>
                </div>

                <div className="field">
                    <label className="label">Password</label>
                    <div className="control">
                        <input onInput={onInputHandler} name={"password"} className="input" type="password"
                               placeholder="Text input"/>
                    </div>
                </div>

                <div className="field is-grouped">
                    <div className="control">
                        <button className="button is-link">Register</button>
                    </div>
                </div>

            </form>
        </div>
    )
}
export default UserRegistrationPage
