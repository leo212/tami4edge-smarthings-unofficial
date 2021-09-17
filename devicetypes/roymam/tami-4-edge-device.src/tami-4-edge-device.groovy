/**
 *  Tami 4 Edge Boiler (Unofficial) - Device Handler
 *
 *  Copyright 2021 Roymam
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Author: Lior Tamam
 */
metadata {
	definition (name: "Tami 4 Edge Device", namespace: "roymam", author: "Lior Tamam", cstHandler: true) {
    	capability "Actuator"
        capability "Switch"
	}
    
	simulator {
		
	}

	tiles {
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "idle", label:'${name}', action:"switch.on", icon:"st.switches.switch.off", backgroundColor:"#00A0DC", nextState:"boiling"
			state "boling", label:'${name}', action:"switch.off", icon:"st.switches.switch.ob", backgroundColor:"#ffffff", nextState:"idle"
		}
        
        main "switch"
		details "switch"
	}
    
}

def installed() {
	log.debug "installed device: ${device.deviceNetworkId}"
}

def on() {
	log.debug "boling device - deviceId: ${device.deviceNetworkId}"
    log.debug "refreshToken: ${parent.state.refreshToken}"
	boilWater()
	sendEvent(name: "switch", value: "on")
}

def off() {
	sendEvent(name: "switch", value: "off")
}

def boilWater() {
	log.debug "boilWater"
  
  // refresh token 
	parent.runRefreshToken(parent.state.refreshToken)
  
  // boil water 
  parent.runRemoteAction("POST", "device/${device.deviceNetworkId}/startBoiling","")
}