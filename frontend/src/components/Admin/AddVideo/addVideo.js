/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React, {Component} from 'react'
import {Link, withRouter} from 'react-router-dom';
import MediaService from "../../../repository/MediaService/mediaService";

class AddVideo extends Component {
    constructor(props) {
        super(props);
        this.state = props.state;
        this.setState({
            "waitResponse": false,
            "errorMessage": null,
        })
    }

    componentDidMount() {
        this.setState({
            "errorMessage": null
        })
    }

    handleTermOnChange = (e) => {
        e.preventDefault();
        const paramName = e.target.name;
        const paramValue = e.target.value;
        this.setState(state => {
            return {
                ...state,
                [paramName]: paramValue,
            }
        });
    };

    onFileChangeHandler = (e) => {
        e.preventDefault();
        this.setState({
            selectedFile: e.target.files[0]
        });
    };

    onSubmit = (e) => {
        e.preventDefault();

        this.setState({
            "waitResponse": true,
            "errorMessage": null,
        });

        let hq = document.getElementById("hq").checked;
        let lq = document.getElementById("lq").checked;
        const formData = new FormData();
        formData.append("file", this.state.selectedFile);
        formData.append("name", this.state.name);
        formData.append("description", this.state.description);
        formData.append("imdbLink", this.state.imdbLink);
        formData.append("imgLink", this.state.imgLink);
        formData.append("hq", hq);
        formData.append("lq", lq);
        MediaService.saveMovie(formData)
            .then(res => {
                MediaService.loadMedia().then(resp => {
                    this.setState(state => {
                        let media = resp.data;
                        return {
                            "media": media,
                        }
                    })
                }).catch(error => {
                    alert(error);
                });
                this.props.history.push("/admin");
            })
            .catch(error => {
                debugger;
                this.setState({
                    "errorMessage": error.response.data
                });
            }).finally(fin => {
            this.setState({
                "waitResponse": false,
            })
        })
    };

    render() {
        return (
            <div className="container border-warning" style={{border: "1px solid black", padding: "1em"}}>
                <div className="form-group">
                    <div className="col-md-1"/>
                    {(this.state.errorMessage !== null ? <div>
                        <h3 className="text-danger">
                            An error occured
                        </h3>
                        <div className="text-danger">
                            {this.state.errorMessage}
                        </div>
                    </div> : <div/>)}
                    {(!this.state.waitResponse ?
                        <form>
                            <div className="form-group ">
                                <h4 className="text-upper text-left text-danger">Add Video</h4>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="video"
                                       className="col-sm-4 offset-sm-1 text-right text-dark">Name</label>
                                <div className="col-sm-6">
                                    <input type="text" onChange={this.handleTermOnChange}
                                           className="form-control" id="video" name={"name"}
                                           placeholder="Video name" required maxLength="50"/>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="description"
                                       className="col-sm-4 offset-sm-1 text-right text-dark">Description</label>
                                <div className="col-sm-6">
                                    <input type="text" onChange={this.handleTermOnChange}
                                           className="form-control" id="description" name={"description"}
                                           placeholder="Video description" required maxLength="2000"/>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="imgLink" className="col-sm-4 offset-sm-1 text-right text-dark">Image
                                    Link</label>
                                <div className="col-sm-6">
                                    <input type="text" onChange={this.handleTermOnChange}
                                           className="form-control" id="imgLink" name={"imgLink"}
                                           placeholder="Image link" required maxLength="1000"/>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="imgLink" className="col-sm-4 offset-sm-1 text-right text-dark">High
                                    Quality</label>
                                <div className="col-sm-6">
                                    <input type="checkbox" className="form-control" id="hq" name={"qualities"}/>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="imgLink" className="col-sm-4 offset-sm-1 text-right text-dark">Low
                                    Quality</label>
                                <div className="col-sm-6">
                                    <input type="checkbox" className="form-control" id="lq" name={"qualities"}/>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="file" className="col-sm-4 offset-sm-1 text-right text-dark">Video
                                    File</label>
                                <div className="col-md-6">
                                    <input type="file" className="form-control fa fa-file" name="file"
                                           onChange={this.onFileChangeHandler} required/>
                                </div>
                            </div>

                            <div className="form-group row">
                                <div
                                    className="offset-sm-1 col-sm-1  text-center">
                                </div>
                                <div
                                    className="offset-sm-1 col-sm-1  text-center">
                                </div>
                                <div
                                    className="offset-sm-1 col-sm-1  text-center">
                                    <button onClick={this.onSubmit}
                                            type="button"
                                            className="btn btn-outline-success text-upper">
                                        Save
                                    </button>
                                </div>
                                <div
                                    className="offset-sm-1 col-sm-1  text-center">
                                    <button
                                        type="reset"
                                        className="btn btn-outline-warning text-upper">
                                        Reset
                                    </button>
                                </div>
                                <div
                                    className="offset-sm-1 col-sm-1  text-center">
                                    <Link to={"/admin"}
                                          className="btn btn-outline-danger text-upper">
                                        Cancel
                                    </Link>
                                </div>
                            </div>
                        </form>
                        :
                        <div style={{justifyContent: 'center', alignItems: 'center', height: '60vh'}}
                             className="container text-black-50">
                            <div><img alt="" style={{width: "15%"}}
                                      src={require('../../../assets/loading.gif')}/>
                            </div>
                            <br/>
                            <h2 className="text-warning">
                                Please wait while we upload the video
                            </h2>
                            <br/>
                            <div className="text-black-50">
                                This may take a while, please be patient
                            </div>
                            <div className="text-info text-sm-center">
                                <p>Why does it take so long?</p>
                                <p>
                                    1. Upload video
                                </p>
                                <p>
                                    2. Encode qualities
                                </p>
                                <p>
                                    3. Encrypt files
                                </p>
                                <p>
                                    4. Create master file
                                </p>
                            </div>
                        </div>)}
                </div>
            </div>
        )
    }
}

export default withRouter(AddVideo);