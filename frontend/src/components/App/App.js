import React, {Component} from 'react';
import {Route, Router} from 'react-router-dom'
import '../../App.css';
import Header from "../Header/header";
import Media from "../Media/media";
import Video from "../Media/Video/video";
import Login from "../Login/login";
import Admin from "../Admin/admin";
import AdminVideo from "../Admin/Media/Video/video";
import AdminMedia from "../Admin/Media/media";
import {createBrowserHistory} from "history";

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
const history = createBrowserHistory();

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            role: ""
        }
    };

    render() {
        return (
            <div className="App">
                <Router history={history}>
                    <div>
                        <Header/>
                        <main role="main">
                            <div className="container">
                                <Route path="/" exact render={() => <Media/>}/>
                                <Route path="/media/:videoID" exact render={() => <Video/>}/>
                                <Route path="/login" exact render={() => <Login/>}/>
                                <Route path="/admin" exact render={() => <Admin/>}/>
                                <Route path="/admin/media/:videoID" exact render={() => <AdminVideo/>}/>
                                <Route path="/admin/media" exact render={() => <AdminMedia/>}/>
                            </div>
                        </main>
                    </div>
                </Router>
            </div>
        );
    }
}

export default App;
