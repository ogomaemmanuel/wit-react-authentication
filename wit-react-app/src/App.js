
import "bulma/css/bulma.min.css"
import LoginPage from "./components/LoginPage";
import BlogPage from "./components/BlogPage";
import CreateBlogPage from "./components/CreateBlogPage";
import HomePage from "./components/Home";
import UserRegistrationPage from "./components/UserRegistrationPage";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import RouterGuard from "./components/RouterGuard";

function App() {


  return (

      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomePage />}>
            <Route index element={<LoginPage />}/>
            <Route path="blogs" element={
                <RouterGuard>
                <BlogPage />
                </RouterGuard>
            } />
            <Route path="create-blog" element={<CreateBlogPage />} />
            <Route path="register" element={<UserRegistrationPage />} />
            <Route path="*" element={<div> Page Not Found </div>} />
          </Route>
        </Routes>
      </BrowserRouter>
  );
}

export default App;
