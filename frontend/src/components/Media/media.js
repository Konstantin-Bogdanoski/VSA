import React, {Component} from 'react';
import Video from "./Video/video";
import MediaService from "../../repository/MediaService/mediaService";

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
class Media extends Component {
    constructor(props) {
        super(props);
        this.setState(state => {
            let movies = [];
            return {
                ...state, movies
            }
        })
    }

    componentDidMount() {
        this.loadMedia();
    }

    loadMedia = () => {
        MediaService.loadMedia().then(resp => {
            let newMovies = resp.map(movie => {
                return <Video movie={movie}/>
            });
            this.setState(state => {
               return{
                   "movies": newMovies
               }
            })
        }).catch(error => {
            alert(error);
        })
    };

    render() {
        return (
            <div className="container">
                <div className="row">
                    {this.state.movies}
                </div>
            </div>
        );
    }
}

export default Media;