import React, {Component} from 'react';
import MediaService from "../../../../repository/MediaService/mediaService";
import videojs from "video.js";

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
class VideoDetails extends Component {
    video = null;

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        let id = window.location.href.split("/")[4];
        this.setState(state => {
            let video = null;
            return {
                ...state, video
            }
        });
        this.fetchVideo(parseInt(id));
        this.player = videojs(this.videoNode, this.props, function onPlayerReady() {
            console.log('onPlayerReady', this)
        });
    }

    componentWillUnmount() {
        if (this.player) {
            this.player.dispose()
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        let id = window.location.href.split("/")[4];
        this.fetchVideo(parseInt(id));
    }

    fetchVideo(id) {
        MediaService.loadMovie(id).then(resp => {
            this.video = resp.data;
        }).catch(error => {
            alert(error)
        });
    }

    componentWillReceiveProps(newProps) {
        if (this.player) {
            this.player.src({
                type: newProps.video.mime_type,
                src: newProps.video.video_url
            });
        }
    }

    render() {
        return (
            <div>
                <div data-vjs-player>
                    <video ref={node => this.videoNode = node} className="video-js"/>
                </div>
            </div>
        );
    }
}

export default VideoDetails;