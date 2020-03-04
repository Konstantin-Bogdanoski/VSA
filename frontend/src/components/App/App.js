import React, {Component} from 'react';
import {Route, Router} from 'react-router-dom'
import '../../App.css';
import Header from "../Header/header";
import Media from "../Media/media";
import Video from "../Media/Video/video";
import Login from "../Login/login";
import Admin from "../Admin/admin";
import AdminVideo from "../Admin/Video/video";
import {createBrowserHistory} from "history";
import VideoDetails from "../Media/Video/VideoDetails/videoDetails";
import MediaService from "../../repository/MediaService/mediaService";
import AddVideo from "../Admin/AddVideo/addVideo";

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
const history = createBrowserHistory();

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            role: "",
            media: [],
            waitResponse: false,
        }
    };

    componentDidMount() {
        this.loadMedia();
    }

    loadMedia = () => {
        this.setState({
            "waitResponse": true
        });

        MediaService.loadMedia().then(resp => {
            this.setState(state => {
                let media = resp.data;
                return {
                    "media": media,
                    "waitResponse": false
                }
            })
        }).catch(error => {
            alert(error);
        })
    };

    updateVideo = ((updatedVideo) => {
        this.setState({
            "waitResponse": true
        });

        MediaService.editMovie(updatedVideo).then(resp => {
            const newVideo = resp.data;
            this.setState((prevState) => {
                const newMedia = prevState.media.map(video => {
                    if (video.id === newVideo.id) {
                        return newVideo;
                    }
                    return video;
                });
                return {
                    "media": newMedia,
                    "waitResponse": false
                }
            });
        });
    });

    deleteVideo = ((id) => {
        this.setState({
            "waitResponse": true
        });

        MediaService.deleteMovie(id).then();
        this.setState((prevState) => {
            const newMedia = prevState.media.filter((video) => {
                return video.id !== id;
            });
            return {
                "media": newMedia,
                "waitResponse": false
            }
        });
    });

    render() {
        return (
            <div className="App text-white" style={{height: 100 + "vh"}}>
                <Router history={history}>
                    <div>
                        <Header/>
                        <main id="main" role="main" className="content glyphicon-fullscreen">
                            {(!this.state.waitResponse ? <div className="content">
                                    <Route path="/" exact render={() => <Media videos={this.state.media}/>}/>
                                    <Route path="/media/:id" exact render={() => <VideoDetails/>}/>
                                    <Route path="/login" exact render={() => <Login/>}/>
                                    <Route path="/admin" exact render={() => <Admin videos={this.state.media}
                                                                                    onDelete={this.deleteVideo}/>}/>
                                    <Route path="/admin/add" exact render={() => <AddVideo state={this.state}/>}/>
                                    <Route path="/admin/media/:id" exact render={() => <AdminVideo/>}/>
                                </div> : <div>
                                    Please wait while we process your request
                                </div>
                            )}
                        </main>
                    </div>
                </Router>
            </div>
        );
    }
}

export default App;
