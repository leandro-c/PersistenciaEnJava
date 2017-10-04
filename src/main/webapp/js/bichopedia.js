var EspecieFullBox = React.createClass({
  getInitialState: function() {
    return {data: []};
  },
  componentDidMount: function() {
    var url = "/api/especie/" + this.props.especie;
    $.ajax({
      url: url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(url, status, err.toString());
      }.bind(this)
    });
  },

  createBicho: function() {
    var nombre = this.state.bichoNombre;
    var url = "/api/especie/" + this.props.especie + "/" + nombre;
    $.ajax({
      url: url,
      dataType: 'json',
      cache: false,
      method: "POST",
      success: function(data) {
        this.setState({data: data.especie});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(url, status, err.toString());
      }.bind(this)
    });
  },

  handleNombreChange: function(e) {
     this.setState({bichoNombre: e.target.value});
  },

  render: function() {
    return (
      <div className="especieFullBox">
        <img src={this.state.data.urlFoto} />
        <div>
          <span className="especieNombre">{this.state.data.nombre}</span>
          <dl>
            <dt>Altura</dt><dd>{this.state.data.altura}</dd>
            <dt>Peso</dt><dd>{this.state.data.peso}</dd>
            <dt>Tipo</dt><dd>{this.state.data.tipo}</dd>
            <dt>Cantidad Bichos</dt><dd>{this.state.data.cantidadBichos}</dd>
          </dl>
          <br />

          <input
            type="text"
            placeholder="Nombre bicho"
            onChange={this.handleNombreChange} />
          <ReactBootstrap.Button onClick={this.createBicho} className="crearBicho" >Crear bicho</ReactBootstrap.Button>
        </div>
      </div>
    );
  }
});

var EspecieBox = React.createClass({
  getInitialState: function() {
     return { showModal: false };
  },
  close: function() {
     this.setState({ showModal: false });
  },
  open: function() {
     this.setState({ showModal: true });
  },

  render: function() {
    return (
      <div>
        <div className="especieBox" onClick={this.open}>
          <img src={this.props.data.urlFoto} />
          {this.props.data.nombre}
        </div>
        <ReactBootstrap.Modal show={this.state.showModal} onHide={this.close} dialogClassName="especieFullBoxModal">
          <EspecieFullBox especie={this.props.data.nombre} />
        </ReactBootstrap.Modal>
      </div>
    );
  }
});

var EspecieForm = React.createClass({
  getInitialState: function() {
     return { nombre: '', peso: null, altura: null, urlFoto: '', energia: null, tipo: null};
  },

  handlePesoChange: function(e) {
    this.setState({peso: parseInt(e.target.value)});
  },
  handleAlturaChange: function(e) {
    this.setState({altura: parseInt(e.target.value)});
  },
  handleNombreChange: function(e) {
    this.setState({nombre: e.target.value});
  },
  handleFotoChange: function(e) {
    this.setState({urlFoto: e.target.value});
  },
  handleEnergiaChange: function(e) {
    this.setState({energia: parseInt(e.target.value)});
  },
  handleTipoChange: function(e) {
    this.setState({tipo: e.target.value});
  },

  handleSubmit: function(e) {
      e.preventDefault();
      var especie = {
        nombre: this.state.nombre.trim(),
        altura: this.state.altura,
        peso: this.state.peso,
        urlFoto: this.state.urlFoto,
        energiaInicial: this.state.energia,
        tipo: this.state.tipo
      };
      console.log(JSON.stringify(especie));

      $.ajax({
        url: '/api/especie',
        contentType: 'application/json',
        dataType: 'json',
        cache: false,
        method: "POST",
        data: JSON.stringify(especie),
        success: function(data) {
          console.log("YEEY")
        }.bind(this),
        error: function(xhr, status, err) {
          console.error("/api/especie", status, err.toString());
        }.bind(this)
      });
  },

  render: function() {
    return (
      <form className="especieForm" onSubmit={this.handleSubmit}>
        <h4>Nueva especie</h4>
        <dl>
          <dt>Nombre</dt>
          <dd><input type="text" placeholder="Nombre" value={this.state.nombre} onChange={this.handleNombreChange} /></dd>
          <dt>Altura</dt>
          <dd><input type="number" placeholder="Altura" value={this.state.altura} onChange={this.handleAlturaChange} /></dd>
          <dt>Peso</dt>
          <dd><input type="number" placeholder="Peso" value={this.state.peso} onChange={this.handlePesoChange} /></dd>
          <dt>Energia inicial</dt>
          <dd><input type="number" placeholder="Energia inicial" value={this.state.energia} onChange={this.handleEnergiaChange} /></dd>
          <dt>Imagen</dt>
          <dd><input type="url" placeholder="Imagen" value={this.state.urlFoto} onChange={this.handleFotoChange} /></dd>
          <dt>Tipo</dt>
          <dd><select placeholder="Tipo" onChange={this.handleTipoChange}>
            <option value=""></option>
            <option value="FUEGO">FUEGO</option>
            <option value="AGUA">AGUA</option>
            <option value="ELECTRICIDAD">ELECTRICIDAD</option>
            <option value="TIERRA">TIERRA</option>
            <option value="PLANTA">PLANTA</option>
          </select></dd>
        </dl>
        <input type="submit" value="Crear especie" className="submitEspecie" />
      </form>
    );
  }
});

var Bichopedia = React.createClass({
  getInitialState: function() {
    return {data: [], showEspecieForm:false};
  },
  componentDidMount: function() {
    this.loadEspeciesFromServer();
    setInterval(this.loadEspeciesFromServer, this.props.pollInterval);
  },
  loadEspeciesFromServer: function() {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },

  openEspecieForm: function() {
    this.setState({ showEspecieForm: true });
  },
  closeEspecieForm: function() {
     this.setState({ showEspecieForm: false });
  },

  render: function() {
    var boxes = this.state.data.map(function(especie) {
        return (
          <EspecieBox data={especie} key={especie.nombre} />
        );
    });

    return (
      <div className="bichoPedia">
        <h4>Bichopedia - Especies</h4>
        <img id="logo" src="image/logo-bichomon.png" />
        <div className="boxes">
          {boxes}
        </div>
        <ReactBootstrap.Button onClick={this.openEspecieForm}>Nueva especie</ReactBootstrap.Button>
        <ReactBootstrap.Modal show={this.state.showEspecieForm} onHide={this.closeEspecieForm} bsSize="small">
          <EspecieForm />
        </ReactBootstrap.Modal>
      </div>
    );
  }
});

var BichopediaModal = React.createClass({
  getInitialState: function() {
     return { showModal: false };
  },
  close: function() {
     this.setState({ showModal: false });
  },
  open: function() {
     this.setState({ showModal: true });
  },

  render: function() {
   return (
     <div>
        <ReactBootstrap.Button bsStyle="primary" bsSize="large" onClick={this.open}>Bichopedia</ReactBootstrap.Button>
        <ReactBootstrap.Modal show={this.state.showModal} onHide={this.close} bsSize="large">
          <Bichopedia url="/api/especie" pollInterval={2000}/>
        </ReactBootstrap.Modal>
     </div>
   );
 }
})

ReactDOM.render(
  <BichopediaModal />,
  document.getElementById('content')
);
