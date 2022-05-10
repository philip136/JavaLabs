import Exceptions.EntityNotFoundException;
import Exceptions.IncorrectNameException;
import Exceptions.PowerException;
import Models.Room;
import Models.Unit;
import lombok.Getter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeControlUnit {
    /** Список все комнат. */
    @Getter
    private List<Room> rooms;
    /** Конструктор блока управление домом. */
    public HomeControlUnit() {
        rooms = new ArrayList<>();
    }
    /** Метод для добавление новых комнат
     * @param roomName Название комнаты.
     * @throws IncorrectNameException возникнет если у комнаты не будет названия.
     */
    public void addNewRoom(String roomName) throws IncorrectNameException {
        if (!roomIsExistOnHome(roomName)) {
            rooms.add(new Room(roomName));
        }
    }

    /** Метод для добавления нового устройства в комнату
     * @param unitName Название устройства.
     * @param unitPower Мощность устройства.
     * @param roomName Название комнаты.
     * @throws IncorrectNameException возникнет если у устройства не будет имени.
     * @throws PowerException возникнет если мощность устройства будет равна 0 либо мощность будет меньше 0.
     * @throws EntityNotFoundException возникнет если комната не будет найдена.
     */
    public void addNewUnit(String unitName, int unitPower, String roomName) throws IncorrectNameException,
                                                                                    PowerException,
                                                                                    EntityNotFoundException
    {
        if (roomIsExistOnHome(roomName)) {
            Room room = findRoomByName(roomName);
            Unit unit = new Unit(unitName, unitPower, room);
            room.assignUnitToRoom(unit);
        }
    }
    /** Метод для включения устройства по идентификатору
     * @param unitId: Идентификатор устройства.
     * @throws EntityNotFoundException возникнет если устройство не будет найдено.
     */
    public void powerOnUnit(int unitId) throws EntityNotFoundException {
        Unit unit = findUnitInRoomById(unitId);
        unit.powerOnUnit();
    }

    public void powerOffUnit(int unitId) throws EntityNotFoundException {
        Unit unit = findUnitInRoomById(unitId);
        unit.powerOffUnit();
    }

    /** Метод для удаления устройства из комнаты
     * @param unitId Идентификатор устройства.
     * @throws EntityNotFoundException возникнет если устройство не будет найдено.
     */
    public void deleteUnitFromRoom(int unitId) throws EntityNotFoundException{
        for (Room room: rooms) {
            for (Unit unit: room.getRoomUnits()) {
                if (unit.getUnitId() == unitId) {
                    unit.removeRoomFromUnit();
                    room.deleteUnitFromRoom(unit);
                    return;
                }
            }
        }
        throw new EntityNotFoundException(String.format("Unit with id %s was not found", unitId));
    }

    /** Проверяем добавлена ли комната
     * @param roomName Название комнаты.
     */
    private boolean roomIsExistOnHome(String roomName) {
        try {
            findRoomByName(roomName);
            System.out.println(String.format("Room with name %s already exist", roomName));
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }
    /** Поиск комнаты по имени комнаты.
     * @param roomName Название комнаты.
     * @throws EntityNotFoundException возникнет если комнаты с таким названием нет.
     */
    private Room findRoomByName(String roomName) throws EntityNotFoundException {
        for (Room room: rooms) {
            if (room.getRoomName().equalsIgnoreCase(roomName)) return room;
        }
        throw new EntityNotFoundException(String.format("Room with %s name not found", roomName));
    }
    /** Метод для поиска устройства по имени устройства.
     * @param unitName: Имя устройства.*/
    public void findUnit(String unitName){
        List<Unit> units = getAllUnits();
        System.out.println("Was found the next units with name like:");
        for (Unit unit : units){
            if (unit.getUnitName().contains(unitName) || unit.getUnitName().equalsIgnoreCase(unitName))
                System.out.println(unit);
        }
    }
    /** Поиск устройства по индентификатору.
     * @param unitId Идентификатор устройства.
     * @throws EntityNotFoundException возникнет если устройство с таким идентификатором нет.
     */
    private Unit findUnitInRoomById(int unitId) throws EntityNotFoundException {
        for (Room room: rooms) {
            for (Unit unit: room.getRoomUnits()) {
                if (unit.getUnitId() == unitId) return unit;
            }
        }
        throw new EntityNotFoundException(String.format("Unit with %s id not found", unitId));
    }

    /** Получить список всех устройст */
    public List<Unit> getAllUnits() {
        List<Unit> units = new ArrayList<>();
        for (Room room: rooms) {
            for (Unit unit: room.getRoomUnits()) {
                units.add(unit);
            }
        }
        return units;
    }
    /** Метод для вывода всех устройств */
    public void printAllUnits() {
        for (Unit unit: getAllUnits()) System.out.println(unit);
    }
    /** Сортируем устройства по мощности */
    public void printAllSortedUnits() {
        System.out.println("Was powered on sorted list by power decrement");
        List<Unit> units = getAllUnits();
        Collections.sort(units);
        for (Unit unit: units) {
            if (unit.isPowerOn()) System.out.println(unit);
        }
    }
    /** Метод для вывода общей мощности вклбюченных устройст */
    public void showActualPower() {
        int totalPower = 0;
        List<Unit> units = getAllUnits();
        for (Unit unit : units) {
            if (unit.isPowerOn()) totalPower = totalPower + unit.getPower();
        }
        printAllSortedUnits();
        System.out.println();
        System.out.println(String.format("Total power consumption is: %s wats.", totalPower));
        }
    }
