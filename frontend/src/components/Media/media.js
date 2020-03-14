import React from 'react';
import Video from "./Video/video";

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
const Media = (props) => {
    let videos = props.videos.map(video => {
        return <Video video={video}/>
    });
    return (
        <div className="container-fluid">
            {(videos === null || videos === undefined || videos.length === 0 ? <div className="container">
                    <h1 className="text-center text-info">No videos available</h1>
                </div> :
                <div className="row">
                    {videos}
                </div>)}
        </div>
    );
};

export default Media;