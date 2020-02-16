import React, {Component} from 'react';
import Video from "./Video/video";
import MediaService from "../../repository/MediaService/mediaService";

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
const Media = (props) => {
    let videos = props.videos.map(video => {
        return <Video video={video}/>
    });
    return (
        <div className="container">
            <div className="row">
                {videos}
            </div>
        </div>
    );
};

export default Media;