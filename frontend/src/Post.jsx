import React, { Component } from "react";
import axios from 'axios';
import PropTypes from "prop-types";
import { Card, Segment, Grid, Divider, Icon } from "semantic-ui-react";
import InstagramEmbed from 'react-instagram-embed';

class Post extends Component {
    constructor(props) {
        super(props);
        this.state = {
            content: ""
        }
        this.showMedia = this.showMedia.bind(this)
        this.getDate = this.getDate.bind(this)
    }

    // componentDidMount() {
    //     this.showMedia(this.props.content.shortcode)
    // }

    showMedia(shortcode) {
        // fetch("http://instagram.com/p/" + shortcode + "/media/?size=m")
        //     .then(function (response) {
        //         console.log(response)
        //         return response.text();
        //     })
        //     .then(function (data) {
        //         console.log("data: ");
        //         this.setState({ content: data });
        //     }.bind(this))
        axios.get("http://instagram.com/p/" + shortcode + "/embed").then(function (response) {
            console.log(response.request.res.responseUrl);
        }).catch(function (no200) {
            console.error("404, 400, and other events");
        });
    }
    getDate(timestamp) {
        let date = new Date(timestamp * 1000);
        return date.toLocaleString();
    }

    // render() {
    //     return (
    //         <div>
    //             <div dangerouslySetInnerHTML={{ __html: this.state.content }} />
    //         </div>
    //     )
    // };

    render() {
        return (


            <Segment inverted size="small">
                <Grid columns={2} stackable textAlign='center'>
                    <Divider vertical></Divider>

                    <Grid.Row verticalAlign='middle'>
                        <Grid.Column>
                            <InstagramEmbed
                                url={"https://instagram.com/p/" + this.props.content.shortcode}
                                clientAccessToken='886643848790422|2ac45e62e0571c89df04ad1be02f752f'
                                maxWidth={300}
                                hideCaption={true}
                                containerTagName='div'
                                protocol=''
                                injectScript
                                onLoading={() => { }}
                                onSuccess={() => { }}
                                onAfterRender={() => { }}
                                onFailure={() => { }}
                            />
                        </Grid.Column>

                        <Grid.Column>
                            <Card key={this.props.content.shortcode}>
                                <Card.Content>
                                    <Card.Meta>
                                        <Icon color='red' name='like' /> {this.props.content.likes + "   "}
                                        <Icon name='comments' /> {this.props.content.comments}
                                        {this.props.content.is_video && " views: " + this.props.content.views}
                                    </Card.Meta>
                                </Card.Content>

                                <Card.Content description={this.props.content.description} />
                                <Card.Content extra>
                                    Posted by user {this.props.content.owner_username} on {this.getDate(this.props.content.taken_at_timestamp)}
                                </Card.Content>
                                <Card.Content extra>
                                    <a target="_blank" href={"http://instagram.com/p/" + this.props.content.shortcode}>View this post on Intagram</a>
                                </Card.Content>
                            </Card>
                        </Grid.Column>
                    </Grid.Row>
                </Grid>
            </Segment>
        );
    }

}

Post.propTypes = {
    content: PropTypes.object.isRequired,
};

export default Post