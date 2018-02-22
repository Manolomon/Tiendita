import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Venta implements Serializable {
    private int idVenta;
    private float total;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date fechaCompra;
    private ArrayList<Producto> Factura = new ArrayList<Producto>();
    private ArrayList<Integer> Cantidades = new ArrayList<Integer>();

    public Venta(int idVenta) {
        this.idVenta = idVenta;
        this.fechaCompra = new Date();
    }

    public int getIdVenta() {
        return this.idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public float getTotal() {
        return this.total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public ArrayList<Producto> getFactura() {
        return this.Factura;
    }

    public void setFactura(ArrayList<Producto> Factura) {
        this.Factura = Factura;
    }

    public Date getFechaCompra() {
        return this.fechaCompra;
    }

    public void setFechaCompra(Date fechCompra) {
        this.fechaCompra = fechCompra;
    }

    public void agregarProducto(Producto p, int i) {
        if (!Factura.contains(p)) {
            Factura.add(p);
            Cantidades.add(i);
        } else {
            int index = Factura.indexOf(p);
            Cantidades.set(index, Cantidades.get(index) + i);
        }
        this.total = this.total + (i * (p.getPrecioVenta()));
    }

    public void generarFactura() {
        Producto p;
        int cant;
        System.out.println("ID Venta: " + this.idVenta);
        System.out.println("Fecha: " + dateFormat.format(this.fechaCompra));
        System.out.printf("%-8s %-20s %-15s %s%n", "ID", "Nombre", "Cantidad", "$ Venta");
        System.out.println(
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (int i = 0; i < Factura.size(); i++) {
            p = Factura.get(i);
            cant = Cantidades.get(i);
            System.out.printf("%-8s %-20s %-15s %s%n", p.getId(), p.getNombre(), cant, p.getPrecioVenta());
        }
        System.out.println(
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\nSubtotal: $"
                        + (this.total * 0.84));
        System.out.println("IVA: $" + (this.total * 0.16));
        System.out.println("Total: $" + this.total);
    }
}