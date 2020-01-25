import React, {Component} from 'react';

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
class VideoDetails extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="text-black-50">
                <div className="container col-sm text-black-50">
                    <div>
                        <h1><strong>Video Title</strong></h1>
                        <div className="text-center">
                            <form method="get" action="1/stream">
                                <select required name="quality" style={{width: "16em", marginBottom: "1em"}}
                                        className="btn btn-outline-success">
                                    <option value="">Select Quality</option>
                                    <option value="1">Low Quality (144p)</option>
                                    <option value="2">Medium Quality (720p)</option>
                                    <option value="3">High Quality (1080p)</option>
                                </select>
                                <br/>
                                <button style={{width: "16em"}}
                                        className="btn btn-success custom-select-lg fa fa-play"/>
                            </form>
                        </div>
                        <div>Upvotes & Downvotes</div>
                        <div className="row">
                            <div className="col-sm-6 font-italic">


                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi congue faucibus
                                vestibulum. Donec aliquet augue vel magna finibus pulvinar ac id massa. Ut auctor et
                                risus sit amet tincidunt. Nunc pharetra quis leo at porta. Morbi semper nulla et dolor
                                tempor ornare. Pellentesque in nunc placerat, venenatis ex non, blandit nisl.
                                Suspendisse sed elit quis neque eleifend varius. Morbi rutrum elementum tempor. Praesent
                                facilisis imperdiet justo, sit amet tincidunt odio.

                                Proin quis metus sed sem egestas condimentum nec a sapien. Etiam ut sapien a orci
                                fermentum accumsan vel vulputate urna. Phasellus varius, massa non vulputate aliquam,
                                leo orci sollicitudin quam, quis volutpat ipsum lectus viverra velit. Ut egestas lacus
                                nec diam gravida, eget sagittis est ultricies. Quisque aliquet dolor turpis, sed laoreet
                                sem pharetra vel. Morbi in vestibulum ligula. Nullam sed mattis nulla, nec euismod
                                ligula. Quisque non diam fermentum, commodo ipsum at, condimentum augue. Nulla consequat
                                malesuada dapibus. Nunc vitae facilisis augue, sed posuere purus. Ut sodales purus sed
                                diam vulputate, a tempus ante varius. Curabitur rhoncus sem eu magna egestas bibendum.
                                Vestibulum luctus tellus vitae elit interdum euismod. Donec et ultrices urna, in
                                fringilla velit. Sed imperdiet egestas quam, vitae lacinia felis scelerisque in.

                                Suspendisse ultricies nibh efficitur ullamcorper pulvinar. Sed scelerisque sagittis
                                lorem, in interdum neque sollicitudin vitae. Vivamus non condimentum tortor, in
                                tincidunt nunc. Suspendisse potenti. Pellentesque vestibulum ultrices velit sit amet
                                tincidunt. Donec rutrum orci magna, eget ultricies risus pellentesque non. Nam gravida
                                ligula eu vehicula ornare. Cras blandit sed orci sit amet faucibus. Integer consectetur
                                felis rhoncus tincidunt rhoncus. Proin suscipit ornare lacus. Sed ut mauris diam.
                                Aliquam nec tortor a sem condimentum eleifend. Etiam eleifend, sapien eget consequat
                                bibendum, tellus risus faucibus nibh, eget sodales eros dolor at mi. Suspendisse aliquet
                                arcu tincidunt, auctor tortor sit amet, posuere purus. Pellentesque non arcu
                                ullamcorper, ultricies enim ac, pellentesque augue. Quisque in urna maximus, tristique
                                purus ac, varius nisi.

                                Vestibulum laoreet bibendum est, sed convallis ligula vehicula sed. Pellentesque
                                efficitur, eros quis dignissim tincidunt, purus nulla rhoncus lectus, vitae lobortis
                                nunc enim id ex. Curabitur eget mollis velit. Sed vitae imperdiet arcu. Sed dapibus nec
                                elit sed fringilla. Fusce vitae orci at dolor viverra auctor non in lacus. Curabitur
                                lobortis mi at malesuada blandit. Suspendisse mollis nisl ac est imperdiet rhoncus. In
                                efficitur lacus felis. Quisque at vestibulum ante. Donec nisl nulla, rhoncus id risus
                                sit amet, semper gravida est. Aenean viverra eget nisi eu vehicula. Phasellus congue
                                tortor non purus tincidunt, vitae faucibus mi tincidunt. Donec accumsan magna vel est
                                lobortis, nec viverra tortor malesuada. Aliquam erat volutpat.

                                Etiam gravida lacus vitae quam consectetur efficitur a at nulla. Etiam rhoncus rutrum
                                ultrices. Nulla congue orci at pretium vehicula. Vestibulum accumsan tempus varius. Cras
                                scelerisque ante blandit aliquam consectetur. Integer sed ipsum molestie, venenatis ex
                                lobortis, varius magna. Aenean egestas orci nec molestie facilisis. In maximus odio quis
                                mattis cursus. Sed efficitur id mi vitae scelerisque. Aliquam semper eros et turpis
                                feugiat vulputate. Integer lectus neque, gravida in tempor vitae, mattis cursus ex.
                            </div>
                            <div className="col-sm-6">
                                <img style={{width: 100 + "%"}}
                                     src="https://images3.alphacoders.com/103/thumb-1920-1037579.jpg" alt=""/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default VideoDetails;