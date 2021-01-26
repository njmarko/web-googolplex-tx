Vue.component("map-component", {
    props: ['inpLongitude', 'inpLatitude', 'readonly' ,'locString'],

    data: function () {
        return {
            inpLocation:{
                'longitude': this.inpLongitude,
                'latitude': this.inpLatitude,
                'readonly': this.readonly,
                'locationString': this.locString,
            },
            firstLoading: true,
        }
    },
    template: ` 
    <div class="div" style="height:100%">   
        <div id="map" class="map">
        </div>
        <div id="popup" class="map-point">
            <svg class="svg-icon map-icon" viewBox="0 0 20 20">
                                <path d="M10,1.375c-3.17,0-5.75,2.548-5.75,5.682c0,6.685,5.259,11.276,5.483,11.469c0.152,0.132,0.382,0.132,0.534,0c0.224-0.193,5.481-4.784,5.483-11.469C15.75,3.923,13.171,1.375,10,1.375 M10,17.653c-1.064-1.024-4.929-5.127-4.929-10.596c0-2.68,2.212-4.861,4.929-4.861s4.929,2.181,4.929,4.861C14.927,12.518,11.063,16.627,10,17.653 M10,3.839c-1.815,0-3.286,1.47-3.286,3.286s1.47,3.286,3.286,3.286s3.286-1.47,3.286-3.286S11.815,3.839,10,3.839 M10,9.589c-1.359,0-2.464-1.105-2.464-2.464S8.641,4.661,10,4.661s2.464,1.105,2.464,2.464S11.359,9.589,10,9.589"></path>
            </svg>
        <a v-bind:class="{ 'd-none': readonly == true }"  href="#" id="popup-closer" class="ol-popup-closer">X</a>
        <div id="popup-content" class="map-content"></div>
    </div>

    </div>
`,
    beforeDestroy() {
        this.$root.$off("mapCallback", this.setPointCallback);
    },
    mounted() {
        var self = this;
    
        // Kosovo
        var kosovoStyle = function () {
            var zoom = map.getView().getZoom();
            var font_size = zoom * 3; // arbitrary value
            return [
                new ol.style.Style({
                    text: new ol.style.Text({
                        font: font_size + 'px Calibri,sans-serif',
                        fill: new ol.style.Fill({
                            color: '#000'
                        }),
                        stroke: new ol.style.Stroke({
                            color: '#fff',
                            width: 2
                        }),
                        text: 'Аутономна покрајина\nСрбије'
                    })
                })
            ];
        };

        var kosovoLayer = new ol.layer.Vector({
            style: kosovoStyle,
            source: new ol.source.Vector({
                features: [
                    new ol.Feature(new ol.geom.Point([2340000, 5250000]))
                ]
            })
        });


    // Popup
        var container = document.getElementById('popup');
        var content = document.getElementById('popup-content');
        var closer = document.getElementById('popup-closer');
    

        var overlay = new ol.Overlay({
            element: container,
            autoPan: true,
            autoPanAnimation: {
            duration: 250,
            },
        });


        closer.onclick = function () {
            overlay.setPosition(undefined);
            closer.blur();
            return false;
        };


        var key = '8f5ebdecb2a44e93bf6cf0438d1326bc';
        var attributions =
          '<a href="https://www.maptiler.com/copyright/" target="_blank">&copy; MapTiler</a> ' +
          '<a href="https://www.openstreetmap.org/copyright" target="_blank">&copy; OpenStreetMap contributors</a>';


        var map = new ol.Map({
            target: 'map',
            layers: [
                new ol.layer.Tile({
                    source: new ol.source.OSM()
                }),
                kosovoLayer,

            ],
            overlays: [overlay],
            view: new ol.View({
                center: ol.proj.fromLonLat([20.45, 44.81]),
                zoom: 6
            })

        });

        function onMoveEnd(evt) {
            var extent = map.getView().calculateExtent(map.getSize());

        };
        map.on('singleclick', function (evt) {
            if (!self.readonly){


                var rawLocation = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');
                
                rawLocation[0] = self.fixLongitude(rawLocation[0]);
                rawLocation[1] = self.fixLatitude(rawLocation[1]);

                var location = {
                    longitude: rawLocation[0],
                    latitude: rawLocation[1]
                }

                content.innerHTML = '<p>You clicked here:</p>';
                console.log(evt.coordinate);

                overlay.setPosition(evt.coordinate);

                self.emitReverseGeocode(rawLocation);
                self.emitLocation(location);
            }
        });



        // MAP CALL BACK
        this.setPointCallback = (location) => {
            self.setPoint(location, overlay, map);
        }

        this.$root.$on('mapCallback', this.setPointCallback);





    },
    methods: {


        setPoint(location, overlay, map) {
            var content = document.getElementById('popup-content');

            locationArray = [this.fixLongitude(location.longitude), this.fixLatitude(location.latitude)];

            var rawCordinates = ol.proj.transform(locationArray, 'EPSG:4326', 'EPSG:3857');

            var oldOverlayLocation = overlay.getPosition()


            overlay.setPosition(rawCordinates);
            if (oldOverlayLocation == undefined){
                map.getView().setCenter(ol.proj.fromLonLat(locationArray));
                map.getView().setZoom(16);
            }

            this.emitReverseGeocode(locationArray);

        },

        emitParams: function () {
            this.$root.$emit('mapLongitude', '123');
        },
        emitLocation: function (location) {
            this.$root.$emit('mapLocation', location);
        },
        emitAddress: function (address) {
            this.$root.$emit('mapAddress', address);
        },

        fixMissingJsonAddress(json){
            if (json.address == undefined){
                json.address = {};
                json.address.city = "";
                json.address.road = "";
                json.address.house_number = "";
                return json;

            }

            if (json.address.city == undefined)
                json.address.city = json.address.village;
            if (json.address.city == undefined)
                json.address.city = json.address.county;
            if (json.address.city == undefined)
                json.address.city = json.address.county;
            if (json.address.city == undefined)
                json.address.city = json.address.country;

            if (json.address.road == undefined)
                json.address.road = "";
            if (json.address.house_number == undefined)
                json.address.house_number = "";  
            if (json.address.city == undefined)
                json.address.city = "";  

            return json;
        },

        emitReverseGeocode(coords) {
            var emitAddr = this.emitAddress;
            var self = this;
            var content = document.getElementById('popup-content');

            const MAPKEY = 'pk.ab20178f4c9ddeeba31a51ab54cfdd67';
            //fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
            fetch('https://us1.locationiq.com/v1/reverse.php?key=' + MAPKEY + '&lat=' + coords[1] + '&lon=' + coords[0] + '&format=json')
                .then(function (response) {
                    return response.json();
                }).then(function (json) {
                    json = self.fixMissingJsonAddress(json);
                    if (self.firstLoading == false){
                        emitAddr(json);
                    } else {
                        self.firstLoading = false;
                    }


                    content.innerHTML = json.address.city + ", " + json.address.road + ", " + json.address.house_number;


                });
        },

        fixLongitude(lon){
            while(lon < -180){
                lon +=360;
            }
            while (lon > 180){
                lon -= 360;
            }
            return lon;
        },

        fixLatitude(lon){
            while(lon < -90){
                lon +=180;
            }
            while (lon > 90){
                lon -= 180;
            }
            return lon;
        },

    },
});