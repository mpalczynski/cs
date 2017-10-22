const React = require('react');
const ReactDOM = require('react-dom');

class App extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
		    data: {},
		    response: '',
		    responseCode: ''
		};

		this.handleInputChange = this.handleInputChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleInputChange(event) {
        const target = event.target;
        var data = this.state.data;
        this.setState({ data : Object.assign({}, this.state.data , {[target.name]: target.value})})
    }

    handleSubmit(event) {
        fetch('/trade', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify([this.state.data])
        })
        .then(response => {
            this.setState({responseCode: response.status});
            return response.json()
        })
        .then(data => {
            this.setState({response: JSON.stringify(data)});
        })
    }

    render() {
        return (
            <form>
                <label>customer:        <input type="text" name="customer" onChange={this.handleInputChange} /></label>
                <label>ccyPair:         <input type="text" name="ccyPair" onChange={this.handleInputChange} /></label>
                <label>product type:    <input type="text" name="type" onChange={this.handleInputChange} /></label>
                <label>amount 1:        <input type="text" name="amount1" onChange={this.handleInputChange} /></label>
                <label>amount 2:        <input type="text" name="amount2" onChange={this.handleInputChange} /></label>
                <label>rate:            <input type="text" name="rate" onChange={this.handleInputChange} /></label>
                <label>tradeDate:       <input type="text" name="tradeDate" onChange={this.handleInputChange} /></label>
                <label>legalEntity:     <input type="text" name="legalEntity" onChange={this.handleInputChange} /></label>
                <label>trader:          <input type="text" name="trader" onChange={this.handleInputChange} /></label>
                <label>payCcy:          <input type="text" name="payCcy" onChange={this.handleInputChange} /></label>
                <label>premiumCcy:      <input type="text" name="premiumCcy" onChange={this.handleInputChange} /></label>
                <label>style:           <input type="text" name="style" onChange={this.handleInputChange} /></label>
                <label>direction:       <input type="text" name="direction" onChange={this.handleInputChange} /></label>
                <label>strategy:        <input type="text" name="strategy" onChange={this.handleInputChange} /></label>
                <label>premium:         <input type="text" name="premium" onChange={this.handleInputChange} /></label>
                <label>valueDate:       <input type="text" name="valueDate" onChange={this.handleInputChange} /></label>
                <label>deliveryDate:    <input type="text" name="deliveryDate" onChange={this.handleInputChange} /></label>
                <label>expiryDate:      <input type="text" name="expiryDate" onChange={this.handleInputChange} /></label>
                <label>excerciseStartDate:  <input type="text" name="excerciseStartDate" onChange={this.handleInputChange} /></label>
                <label>premiumDate:         <input type="text" name="premiumDate" onChange={this.handleInputChange} /></label>
            	<input type="button" value="Validate" onClick={this.handleSubmit} />

            	<label>Result: {this.state.responseCode} <textarea value={this.state.response} /></label>
            </form>
        )
    }
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)