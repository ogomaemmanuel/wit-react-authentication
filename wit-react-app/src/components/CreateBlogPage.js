import React, {useState} from 'react'

const CreateBlogPage = () => {
    const [blog, setBlog] = useState({
        title: "",
        "content": ""
    })
    const onInputHandler = (event) => {
        setBlog(
            prev => {
                return {...prev, ...{[event.target.name]: event.target.value}}
            }
        )
    }

    const onSubmitHandler = async (event) => {
        event.preventDefault();
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(blog)
        };
        const resp = await fetch(`${process.env.REACT_APP_API_ENDPOINT_URL}/api/v1/blogs`, options)
        const responseData = await resp.json();
        console.log(responseData);
    }

    return (
        <div>
            <form onSubmit={onSubmitHandler}>
                <div className="field">
                    <label className="label">Title</label>
                    <div className="control">
                        <input onInput={onInputHandler} name={"title"} className="input" type="text" placeholder="Text input"/>
                    </div>
                </div>
                <div className="field">
                    <label className="label">Content</label>
                    <div className="control">
                        <textarea onInput={onInputHandler} name={"content"} className="textarea" placeholder="Textarea"></textarea>
                    </div>
                </div>

                <div className="field is-grouped">
                    <div className="control">
                        <button className="button is-link">Submit</button>
                    </div>
                </div>
            </form>

        </div>
    )
}

export default CreateBlogPage