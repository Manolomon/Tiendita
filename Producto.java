import java.util.Scanner;
import java.io.Serializable;

public class Producto implements Serializable {
    private int id;
    private String nombre;
    private String descripcion;
    private String unidad;
    private int cantidad;
    private float precioCompra;
    private float precioVenta;
    public static Scanner sc = new Scanner(System.in);

    public Producto(int id, String nombre, String descripcion, String unidad, int cantidad, float precioCompra) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.precioVenta = precioCompra * 2;
    }

    public Producto() {
        System.out.println("Ingrese el id del producto");
        this.id = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese el nombre del producto:");
        this.nombre = sc.nextLine();
        System.out.println("Ingrese una descripción del producto:");
        this.descripcion = sc.nextLine();
        System.out.println("Ingrese la unidad del producto:");
        this.unidad = sc.nextLine();
        System.out.println("Ingrese la cantidad del producto:");
        this.cantidad = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese el precio de compra del producto:");
        this.precioCompra = sc.nextFloat();
        this.precioVenta = precioCompra * 2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad() {
        return this.unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioCompra() {
        return this.precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public float getPrecioVenta() {
        return this.precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }
}