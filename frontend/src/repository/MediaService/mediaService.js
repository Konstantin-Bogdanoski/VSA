import React from 'react';
import axios from '../../axios/axios';
import qs from 'qs';

const MediaService = {
    loadMedia: () => {
        return axios.get("/api/video");
    },
    loadMovie: (id) => {
        return axios.get("/api/video/" + id);
    },
    playMovie: (id, key) => {
        return "To be implemented";
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
        return axios.post("http://localhost:8080/api/admin", newMovie);
    }
};

export default MediaService;