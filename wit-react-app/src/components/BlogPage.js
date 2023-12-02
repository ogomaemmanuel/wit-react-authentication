import React, {useEffect, useState} from 'react'
import axios from "axios";

const BlogPage = (props) => {
    const [blogs, setBlogs] = useState([]);
    const [hasError, setHasError] = useState(false);
    useEffect(() => {
        const fetchBlogs = async () => {

              const  headers= {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${ JSON.parse(localStorage.getItem('user'))?.accessToken}`
                }

                try {

                    const resp = await axios.get(`/api/v1/blogs`, {
                        headers: headers
                    })
                    const data = await resp.data;
                    setBlogs(prev => {
                        return data.content;
                    })
                }catch (error){
                  setHasError(true);
                }
        }
        fetchBlogs()
        // Return a cleanup function to cancel the fetch if the component unmounts
        return () => {
            console.log('Clean up');
        };
    }, [])

    return (
        <div>
            <div className={"blog-control-panel"}>
                <button className={"button is-success"}>Create Blog</button>
            </div>
            {
            blogs.map((blog)=>(
                <div key={blog.id} className="card">
                    <div className="card-content">
                        <div className="content">
                            {blog.content}
                        </div>
                    </div>
                </div>
            ))
            }
        </div>
    )
}

export default BlogPage