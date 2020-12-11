import React, { Component } from 'react'
import axios from 'axios';
import { Input, Statistic, Segment, Grid } from "semantic-ui-react";
import Post from './Post.jsx';

class Search extends Component {
    constructor(props) {
        super(props)
        this.state = {
            query: '',
            docs: []
        }
        this.handleTextChange = this.handleTextChange.bind(this)
        this.handleSubmitQuery = this.handleSubmitQuery.bind(this)
        this.parseData = this.parseData.bind(this)

    }

    handleTextChange(event) {
        this.setState({
            [event.target.name]: event.target.value,
        })
    }
    handleSubmitQuery(evt) {
        if (evt.key === "Enter") {
            let queryText = this.state.query

            if (queryText.length !== 0) {
                axios.post('http://localhost:8080/search', null, {
                    params: {
                        query: queryText
                    }
                }
                ).then(res => {
                    // parse each json string to object and put in array
                    this.parseData(res.data)
                })
            }
        }

    }
    parseData(data) {
        let res = []
        data.map(str => res.push(JSON.parse(str)))
        this.setState({ docs: res })
        console.log(res)
    }

    renderPosts() {
        return this.state.docs.map((p) => (

            <Post
                key={p.shortcode}
                content={p}
            />
        ))
    }

    render() {
        return (
            <Grid>
                <Grid.Row centered>
                    <Statistic>
                        <Statistic.Value>Instagram Post Search</Statistic.Value>
                    </Statistic>
                </Grid.Row>
                <Grid.Row centered>
                    <Grid.Column width={4} />
                    <Grid.Column width={8}>
                        <Input
                            fluid
                            placeholder='Search...'
                            icon='search'
                            type="text"
                            name="query"
                            value={this.state.query}
                            onChange={this.handleTextChange}
                            onKeyPress={this.handleSubmitQuery}
                        />
                    </Grid.Column>
                    <Grid.Column width={4} />
                </Grid.Row>
                <Grid.Row centered>
                    <Grid.Column width={4} />
                    <Grid.Column width={8}>
                        {this.state.docs.length === 0 ? <div /> : <Segment.Group raised>{this.renderPosts()}</Segment.Group>}
                    </Grid.Column>
                    <Grid.Column width={4} />
                </Grid.Row>
            </Grid>
        )
    }
}
export default Search