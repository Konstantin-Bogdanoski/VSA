import axios from '../../axios/axios';
import qs from 'qs';

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */

const MediaService = {
    loadMedia: () => {
        return axios.get("/api/video");
    },
    loadMovie: (id) => {
        return axios.get("/api/video/" + id);
    },
    editMovie(updatedMovie) {
        const data = {
            ...updatedMovie,
        };
        const videoID = updatedMovie.videoID;
        const formParams = qs.stringify(data);
        return axios.patch("/api/admin/" + videoID, formParams, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });
    },
    saveMovie(newMovie) {
        return axios.post("/api/admin/upload", newMovie);
    },
    deleteMovie(id) {
        return axios.delete("/api/admin/" + id)
    }
};

export default MediaService;