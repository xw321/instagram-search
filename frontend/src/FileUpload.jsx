import React, { Component } from 'react'
import Search from './Search'
import axios from 'axios';

class FileUpload extends Component {
    constructor(props) {
        super(props)
        this.state = {
            selectedFile: null,
            rows: [],
            size: 0,
            time: 0,

        };
        this.handleFileChange = this.handleFileChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
        this.handleResponse = this.handleResponse.bind(this)
        this.handleData = this.handleData.bind(this)

    }

    handleFileChange(event) {
        this.setState({
            selectedFile: event.target.files[0],
            rows: [],
            size: 0,
            time: 0
        });

    };

    handleSubmit() {
        if (this.state.selectedFile == null) {
            alert("Choose a file first")
        } else if (!this.state.selectedFile.name.toLowerCase().endsWith('.xml')) {
            alert("Must be XML file")
        }
        else {
            const data = new FormData()
            data.append('file', this.state.selectedFile)
            axios.post('http://localhost:8080/parse', data, {
                // receive two    parameter endpoint url ,form data
            }).then(res => { // then print response status
                this.handleResponse(res)
                this.setState({ selectedFile: null })
            })
        }
    };

    handleResponse(res) {
        if (res.status === 200) {
            this.handleData(res.data)
        } else {
            console.log(res)
        }
    }

    handleData(mapData) {
        let dataArr = []
        Object.entries(mapData).forEach(([key, value]) => {
            if (key === "dataSize") {
                let res1 = Object.values(value)[0]
                this.setState({ size: res1 })
            } else if (key === "dataTime") {
                let res2 = Object.values(value)[0]
                this.setState({ time: res2 })
            } else {
                dataArr.push({ term: key, docs: Object.keys(value) })
            }
        })
        this.setState({ rows: dataArr })
    }



    render() {
        return (
            <div className="container">
                <h1>
                    Upload Your XML File to Index
                </h1>
                <div>
                    <input type="file" onChange={this.handleFileChange} />
                    <button onClick={this.handleSubmit}>
                        Upload!
                    </button>
                </div>

                {this.state.rows.length === 0 ? <div /> :
                    <div>
                        <div>
                            <p />
                            <p>Total Inverted Index size is <span className="font-weight-bold"> {this.state.size} kB</span> ({this.state.rows.length} indexed terms)</p>
                            <p>Total processing time is <span className="font-weight-bold"> {this.state.time} ms ({this.state.time / 1000} second)</span></p>
                        </div>

                        <div className="table-responsive" id="mytable">
                            <table className="table table-bordered">
                                <thead className="thead-dark">
                                    <tr>
                                        <th>Indexed Term</th>
                                        <th>Document IDs</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        this.state.rows.map(
                                            obj =>
                                                <tr key={obj.term}>
                                                    <td>{obj.term}</td>
                                                    <td>{obj.docs.toString()}</td>
                                                </tr>
                                        )
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>}

                {this.state.rows.length === 0 ? <div /> : <Search />}
            </div>
        );
    }
}

export default FileUpload