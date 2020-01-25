import React, {Component} from 'react';

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
class Video extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        // TODO: Link the <a> to the videos location -> /media/{id}
        return (
            <div className="col-md-3 col-6">
                <div>
                    <a href="/media/1" className="card-link text-decoration-none text-white">
                        <div className="card mb-3 col-sm-4 bg-dark"
                             style={{maxWidth: 20 + "em", margin: 1 + "em"}}>
                            <div className="card-header bg-transparent">Video Name</div>
                            <div className="card-body">
                                <img src="https://images3.alphacoders.com/103/thumb-1920-1037579.jpg"
                                     className="card-img" alt=""/>
                            </div>
                            <div className="card-footer bg-transparent">Upvotes & Downvotes</div>
                        </div>
                    </a>
                </div>
            </div>
        );
    }
}

export default Video;