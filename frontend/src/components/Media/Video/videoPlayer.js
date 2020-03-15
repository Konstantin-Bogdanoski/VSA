import React from 'react';
import videojs from 'video.js'
/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
export default class VideoPlayer extends React.Component {
    componentDidMount() {
        this.player = videojs(this.videoNode, this.props, function onPlayerReady() {
        });
    }

    componentWillUnmount() {
        if (this.player) {
            this.player.dispose()
        }
    }

    render() {
        return (
            <div>
                <div data-vjs-player>
                    <video ref={node => this.videoNode = node} className="video-js fullscreen"/>
                </div>
            </div>
        )
    }
}