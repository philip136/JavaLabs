package Models;

import Exceptions.IncorrectNameException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Room {
    /** Название комнаты. */
    @Getter
    private String roomName;
    /** Список устройст которые будут храниться в комнате. */
    @Getter
    private List<Unit> roomUnits;

    /** Конструктор для создания комнаты. */
    public Room (String roomName) throws IncorrectNameException {
        if (roomName == null || roomName == "") {
            throw new IncorrectNameException("Room name can't be empty or null");
        }
        this.roomName = roomName;
        this.roomUnits = new ArrayList<>();
        System.out.println(String.format("Room with name %s has been created", roomName));
        System.out.println(String.format("Additional info: %s", toString()));
    }
    /** Добавление устройства в комнату. */
    public void assignUnitToRoom(Unit unit) {
        roomUnits.add(unit);
        System.out.println(String.format("Unit with name %s has been added in room %s", unit.getUnitName(), roomName));
    }
    /** Удаление устройства из комнаты. */
    public void deleteUnitFromRoom(Unit unit) {
        if (unitIsExistInRoom(unit)) {
            roomUnits.remove(unit);
            System.out.println(String.format("Unit with name %s has been removed from room with name%s",
                    unit.getUnitName(), roomName));
        }
    }
    /** Проверка на то есть ли устройство в комнате. */
    private boolean unitIsExistInRoom(Unit unit) {
        for (Unit un: roomUnits) {
            if (un.equals(unit)) return true;
        }
        System.out.println(String.format("Unit with name %s and with id %s was not found in the room with name %s",
                unit.getUnitName(), unit.getUnitId(), roomName));
        return false;
    }

    /** Переопределяем сравнение для комнат */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomName.equalsIgnoreCase(room.roomName);
    }

    @Override
    public String toString() {
        return String.format("Room{roomName=%s}", roomName);
    }
}
