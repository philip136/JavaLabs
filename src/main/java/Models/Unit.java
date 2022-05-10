package Models;

import Exceptions.IncorrectNameException;
import Exceptions.PowerException;
import lombok.Getter;

public class Unit implements Comparable<Unit> {
    /** Статическая переменная которая указывает начальный идентификатор */
    private static int unitIdCounter = 1;
    /** Идентификатор устройства */
    @Getter
    private int unitId;
    /** Название устройства */
    @Getter
    private String unitName;
    /** Объект комнаты, которой будет принадлежать данное устройство */
    @Getter
    private Room room;
    /** Мощность устройства */
    @Getter
    private int power;
    /** Флаг указывающий рабочее состояние устройства, по умолчанию устройство выключено */
    @Getter
    private boolean isPowerOn = false;
    /** Конструктор устройства
    @param unitName - Строка - Название устройства.
    @param power - Целочисленное значение - Мощность устройства.
    @param room - Объект комнаты.
    **/
    public Unit(String unitName, int power, Room room) throws IncorrectNameException, PowerException {
        if (unitName == null || unitName == "") {
            throw new IncorrectNameException("Unit Name can't be empty or null");
        }
        if (power <= 0) {
            throw new PowerException("Unit Power need to be positive value");
        }
        unitId = unitIdCounter++;
        this.unitName = unitName;
        this.power = power;
        this.room = room;
        System.out.println(String.format("Unit with name %s has been created", unitName));
        System.out.println(String.format("Additional info: %s", toString()));
    }
    /** Удалить устройство из комнаты. */
    public void removeRoomFromUnit() {
        System.out.println(String.format("Unit has been removed from room with name %s", room.getRoomName()));
        room = null;
    }
    /** Включить устройство. */
    public void powerOnUnit() {
        isPowerOn = true;
        System.out.println(String.format("Unit with name %s was turned on", unitName));
    }
    /** Выключить устройство. */
    public void powerOffUnit() {
        isPowerOn = false;
        System.out.println(String.format("Unit with name %s was turned off", unitName));
    }
    /** Переопредление сравнения для устройств. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        if (unitId != unit.unitId) return false;
        if (power != unit.power) return false;
        if (!unitName.equals(unit.unitName)) return false;
        return !(room != null ? !room.equals(unit.room) : unit.room != null);
    }

    /** Сравниваем устройства */
    @Override
    public int compareTo(Unit un) {
        return (this.getPower() - un.getPower());
    }

    @Override
    public String toString() {
        return String.format("Unit{unitId=%s, unitName=%s, room=%s, power=%s, isPowerOn=%s",
                unitId, unitName, room, power, isPowerOn);
    }

}
