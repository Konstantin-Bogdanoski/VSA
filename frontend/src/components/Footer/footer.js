import React, {Component} from 'react';

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
class Footer extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="card-footer footer border-danger" style={{zIndex: "-1000"}}>
                <div style={{width: "30%", display: "inline-block"}} className="text-sm-left">
                    <a target="_blank" rel="noopener noreferrer" href="https://github.com/Konstantin-Bogdanoski/VSA"
                       className="btn btn-dark">
                        <i className="fa fa-github"/>
                    </a>
                </div>
                <div style={{width: "30%", display: "inline-block"}} className="text-sm-center text-danger">
                    Video Streaming Application
                </div>
                <div style={{width: "30%", display: "inline-block"}} className="text-sm-right text-danger">
                    FCSE, 2020
                </div>
            </div>
        );
    }
}

export default Footer;