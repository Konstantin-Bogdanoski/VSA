import React from 'react';
import axios from '../../axios/axios';
const MediaService = {
    loadMedia: () => {
        return axios.get("/api/video");
    },
    loadMovie: (id) => {
        return axios.get("/api/video/" + id);
    },
    playMovie: (id, key) => {
        return "To be implemented";
    }
};

export default MediaService;