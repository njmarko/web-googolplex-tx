Vue.component("map-component", {
	data: function () {
		return {

		}
	},
    template: ` 
    <div class="div">       
        <div id="map" class="map">

        </div>
        <button v-on:click="emitParams">Send message to child 2</button> <br />
    </div>
`
	,
	mounted() {
        var thisComponent = this;

        var style = function () {
            var zoom = map.getView().getZoom();
          var font_size = zoom * 3; // arbitrary value
          return [
            new ol.style.Style({
              text: new ol.style.Text({
                font: font_size + 'px Calibri,sans-serif',
                fill: new ol.style.Fill({ color: '#000' }),
                stroke: new ol.style.Stroke({
                  color: '#fff', width: 2
                }),
                text: 'Аутономна покрајина\nСрбије'
              })
            })
          ];
        };
        
        var vectorLayer = new ol.layer.Vector({
            style: style,
          source: new ol.source.Vector({
              features: [
                new ol.Feature(new ol.geom.Point([2340000, 5250000]))
            ]
          })
        });


		var map = new ol.Map({
			target: 'map',
			layers: [
			new ol.layer.Tile({
				source: new ol.source.OSM()
            }),
            vectorLayer
        
			],
			view: new ol.View({
				center: ol.proj.fromLonLat([20.45, 44.81]),
				zoom: 6
            })

        });
        
        function onMoveEnd(evt) {
            var extent = map.getView().calculateExtent(map.getSize());

            alert(extent);
        };

        map.on('singleclick', function (evt) {        
            var rawLocation = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');
            var location = {
                longitude: rawLocation[0],
                latitude: rawLocation[1]
            }
            
            thisComponent.emitReverseGeocode(rawLocation);
            thisComponent.emitLocation(location);
            
        });

		
	},
	methods: {
		emitParams : function() {
    		this.$root.$emit('mapLongitude', '123');
		},
		emitLocation : function(location) {
    		this.$root.$emit('mapLocation', location);
        },
        emitAddress : function(address) {
    		this.$root.$emit('mapAddress', address);
        },
        

        emitReverseGeocode(coords) {
            var emitAddr = this.emitAddress;

            fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
            .then(function(response) {
                return response.json();
            }).then(function(json) {
                emitAddr(json);
            });
        },
         


	},
});