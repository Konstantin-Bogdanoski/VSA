/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React from 'react';
import axios from '../../axios/axios';

const AuthenticationService = {
    loginUser: (request) => {
        return axios.post('/login', request);
    }
};

export default AuthenticationService;