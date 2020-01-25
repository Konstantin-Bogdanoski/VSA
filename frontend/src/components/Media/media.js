import React, {Component} from 'react';
import Video from "./Video/video";

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
class Media extends Component {
    constructor(props) {
        super(props);
    }

    loadMedia = () => {
        let videos = [];
        for (let i = 0; i < 100; i++) {
            videos.push(<Video/>)
        }
        this.setState(() => {
            return {
                "videos": videos
            }
        });
    };

    render() {
        let videos = [];
        for (let i = 0; i < 100; i++) {
            videos.push(<Video/>)
        }
        return (
            <div className="container">
                <div className="row">
                    {videos}
                </div>
            </div>
        );
    }
}

export default Media;