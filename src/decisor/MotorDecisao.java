package decisor;

import descricao.ModoClima;
import events.sensor.*;
import events.highLevelOrder.*;

public class MotorDecisao {

    private float temperaturaMinima = 18;
    private float temperaturaMaxima = 26;
    private float luminosidadeMinima = 300;
    private float humidadeMaxima = 70;
    private float humidadeSoloMinima = 30;

    public HighLevelOrderEvent analisar(SensorEvent evento) {

        float valor = evento.getValue();

        if (evento instanceof TemperatureSensorEvent) {

            if (valor < temperaturaMinima) {
                return new AreaTemperatureOrderEvent(
                        evento.getArea(),
                        ModoClima.Aquecimento,
                        temperaturaMinima + 2
                );
            }

            if (valor > temperaturaMaxima) {
                return new AreaTemperatureOrderEvent(
                        evento.getArea(),
                        ModoClima.Arrefecimento,
                        temperaturaMaxima - 2
                );
            }

            return new AreaTemperatureOrderEvent(
                    evento.getArea(),
                    ModoClima.Desligado,
                    -1
            );
        }

        if (evento instanceof LightSensorEvent) {
            return new AreaLightingOrderEvent(
                    evento.getArea(),
                    valor < luminosidadeMinima
            );
        }

        if (evento instanceof HumiditySensorEvent) {
            return new AreaVentilationOrderEvent(
                    evento.getArea(),
                    valor > humidadeMaxima
            );
        }

        if (evento instanceof GroundMoistureSensorEvent) {
            return new AreaIrrigationOrderEvent(
                    evento.getArea(),
                    valor < humidadeSoloMinima
            );
        }

        throw new IllegalArgumentException(
                "Tipo de sensor não suportado."
        );

    }
}