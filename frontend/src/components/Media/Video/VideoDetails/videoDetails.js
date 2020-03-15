import React, {useEffect, useState} from "react";
import VideoPlayer from "../videoPlayer";
import MediaService from "../../../../repository/MediaService/mediaService";

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
const VideoDetails = () => {
    let [movie, setMovie] = useState();
    let id = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);

    useEffect(() => {
        MediaService.loadMovie(id).then((data) => {
            const movie = data.data;
            setMovie(movie);
        });
    }, []);

    let videoJsOptions = "";
    if (movie !== undefined) {
        const fileName = "http://localhost/videos/" + movie.fileName;
        videoJsOptions = {
            autoplay: false,
            controls: true,
            sources: [{
                src: fileName,
                type: 'application/x-mpegURL',
            }],
            html5: {
                hls: {
                    overrideNative: true
                }
            }
        };
    }


    return (
        (videoJsOptions !== "" ? <div className="container-fluid text-center">
            <div data-vjs-player>
                <VideoPlayer {...videoJsOptions} />
            </div>
        </div> : <div className="text-info text-center">Please wait while we load the video</div>)
    );
};

export default VideoDetails;