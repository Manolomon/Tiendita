import java.io.File;
import java.util.Date;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Tiendita implements Serializable {
    public static Scanner sc = new Scanner(System.in);
    private String fileName = ".\\Inventario.txt";
    private String file = ".\\Ventas.txt";
    private ArrayList<Producto> Inventario = new ArrayList<Producto>();
    private ArrayList<Venta> Ventas = new ArrayList<Venta>();

    public Tiendita() {
        File archivo = new File(fileName);
        File historialVentas = new File(file);
        try {
            if (archivo.exists()) {
                System.out.println("Leyendo archivo previo...");
                FileInputStream fis = new FileInputStream(fileName);
                ObjectInputStream ois = new ObjectInputStream(fis);
                while (fis.available() > 0) {
                    Inventario.add((Producto) ois.readObject());
                    System.out.println("Objeto encontrado");
                }
                ois.close();
            } else {
                System.out.println("Creando nuevo archivo de Inventario...");
                archivo.createNewFile();
            }
            if (historialVentas.exists()) {
                System.out.println("Leyendo Historial de Ventas previo...");
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                while (fis.available() > 0) {
                    Ventas.add((Venta) ois.readObject());
                    System.out.println("Objeto encontrado");
                }
                ois.close();
            } else {
                System.out.println("Creando nuevo Historial de Ventas...");
                historialVentas.createNewFile();
            }
        } catch (IOException i) {
            System.err.println("Error en la lectura del archivo binario: " + i.getMessage());
        } catch (Exception e) {
            System.err.println("Error de tipo: " + e.getMessage());
        }
    }

    public void guardado() {
        System.out.println("Finalizando guardado...");
        File archivo = new File(fileName);
        File historialVentas = new File(file);
        archivo.delete();
        historialVentas.delete();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            for (int i = 0; i < Inventario.size(); i++) {
                System.out.println("Escribiendo el producto: " + (i + 1));
                oos.writeObject(Inventario.get(i));
            }
            oos.close();
            ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(file));
            for (int i = 0; i < Ventas.size(); i++) {
                System.out.println("Escribiendo venta: " + (i + 1));
                oos2.writeObject(Ventas.get(i));
            }
            oos2.close();
        } catch (IOException i) {
            System.err.println("Error en la escritura del archivo binario: " + i.getMessage());
        } catch (Exception e) {
            System.err.println("Error de tipo: " + e.getMessage());
        }
    }

    public int buscarId(int id) {
        int c = -1;
        for (int i = 0; i < Inventario.size(); i++) {
            if (id == Inventario.get(i).getId()) {
                c = i;
            }
        }
        return c;
    }

    public void agregarProducto() {
        Producto p;
        System.out.println("Ingrese el id del producto");
        int id = sc.nextInt();
        sc.nextLine();
        int index = buscarId(id);
        if (index != -1) {
            p = Inventario.get(index);
            System.out.println("El id ya se ha registrado anteriormente, agregando nueva versión del item: ");
            mostrarDatosIndividuales(index);
            System.out.println("Ingrese el nuevo precio de compra:");
            float nuevoPrecioCompra = sc.nextFloat();
            System.out.println("Ingrese la nueva cantidad:");
            int ingresoCantidad = sc.nextInt();
            sc.nextLine();
            float promedio = ((p.getPrecioCompra() * p.getCantidad()) + (ingresoCantidad * nuevoPrecioCompra))
                    / (ingresoCantidad * p.getCantidad());
            Producto nuevo = new Producto(id, p.getNombre(), p.getDescripcion(), p.getUnidad(),
                    p.getCantidad() + ingresoCantidad, promedio);
            //nuevo.setPrecioVenta((nuevo.getPrecioVenta() + p.getPrecioVenta()) / 2);
            Inventario.set(index, nuevo);
            System.out.println("Inventario Actualizado");
            mostrarDatosIndividuales(index);
        } else {
            System.out.println("Ingrese el nombre del producto:");
            String nombre = sc.nextLine();
            System.out.println("Ingrese una descripción del producto:");
            String descripcion = sc.nextLine();
            System.out.println("Ingrese la unidad del producto:");
            String unidad = sc.nextLine();
            System.out.println("Ingrese la cantidad del producto:");
            int cantidad = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese el precio de compra del producto:");
            float precioCompra = sc.nextFloat();
            Inventario.add(new Producto(id, nombre, descripcion, unidad, cantidad, precioCompra));
        }
    }

    public void modificarProducto(int id) {
        int i = buscarId(id);
        if (i != -1) {
            mostrarDatosIndividuales(i);
            Producto p = new Producto();
            Inventario.set(i, p);
            System.out.println("Elemento modificado...");
        } else {
            System.out.println("Elemento no encontrado...");
        }
    }

    public void eliminarProducto(int id) {
        int i = buscarId(id);
        if (i != -1) {
            Inventario.remove(i);
            System.out.println("Elemento eliminado...");
        } else {
            System.out.println("Elemento no encontrado...");
        }
    }

    public void mostrarDatosIndividuales(int id) {
        Producto p = Inventario.get(id);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("ID: " + p.getId() + "\nNombre: " + p.getNombre() + "\nDescripción: " + p.getDescripcion()
                + "\nUnidad: " + p.getUnidad() + "\nCantidad: " + p.getCantidad() + "\nPrecio de Compra: $"
                + p.getPrecioCompra() + "\nPrecio de Venta: $" + p.getPrecioVenta());
    }

    public void mostrarInventario() {
        Producto p;
        System.out.printf("%-8s %-25s %-45s %-10s %-15s %-10s %s%n", "ID", "Nombre", "Descripción", "Unidad",
                "Cantidad", "$ Compra", "$ Venta");
        System.out.println(
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (int i = 0; i < Inventario.size(); i++) {
            p = Inventario.get(i);
            System.out.printf("%-8s %-25s %-45s %-10s %-15s %-10s %s%n", p.getId(), p.getNombre(), p.getDescripcion(),
                    p.getUnidad(), p.getCantidad(), p.getPrecioCompra(), p.getPrecioVenta());
        }
    }

    public void realizarVenta() {
        int sel = 0, index, id, cuant;
        float dinero;
        boolean exito = false;
        ArrayList<Producto> backup = new ArrayList<Producto>(this.Inventario);
        Producto p;
        Venta compra = new Venta(Ventas.size() + 1);
        System.out.println(
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Venta ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        while (exito != true && sel != 2) {
            System.out.println("Ingrese: \n1) Agregar Producto\n2) Cancelar Venta\n3) Finalizar Venta");
            sel = sc.nextInt();
            sc.nextLine();
            switch (sel) {
            case 1: // Agregar Producto
                System.out.println("Ingrese el id del producto");
                id = sc.nextInt();
                sc.nextLine();
                index = buscarId(id);
                if (index != -1) {
                    System.out.println("Ingrese la cantidad");
                    cuant = sc.nextInt();
                    sc.nextLine();
                    if (cuant <= Inventario.get(index).getCantidad()) {
                        p = backup.get(index);
                        p.setCantidad(p.getCantidad() - cuant);
                        backup.set(index, p);
                        compra.agregarProducto(p, cuant);
                    } else {
                        System.out.println("No hay suficientes productos para realizar la compra");
                    }
                } else {
                    System.out.println("No hay productos registrados con ese id");
                }
                break;
            case 2: // Cancelar Venta
                System.out.println("Compra cancelada");
                break;
            case 3: // Finalizar Venta
                if (compra.getFactura().isEmpty()) {
                    System.out.println("Compra vacía, finalizado sin cambios");
                } else {
                    System.out.println("Finalizando compra");
                    compra.generarFactura();
                    System.out.println("TOTAL: $" + compra.getTotal());
                    System.out.println("Ingrese cantidad recibida");
                    dinero = sc.nextFloat();
                    if (dinero >= compra.getTotal()) {
                        System.out.println("Cambio: $" + (dinero - compra.getTotal()));
                        this.Inventario = new ArrayList<Producto>(backup);
                        Ventas.add(compra);
                        System.out.println("Compra realizada con éxito");
                        exito = true;
                    } else {
                        System.out.println("Cantidad Insuficiente");
                    }
                }
                break;
            default:
                System.out.println("Opción no válida");
                break;
            }
        }
    }

    public void mostrarVentas() {
        Venta v;
        for (int i = 0; i < Ventas.size(); i++) {
            v = Ventas.get(i);
            System.out.println("Venta: " + v.getIdVenta());
            v.generarFactura();
            System.out.println(
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

    public void generarReporte() {
        int ai, mi, di, af, mf, df;
        Venta v;
        System.out.println("Ingrese año de inicio de Reporte");
        ai = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese mes");
        mi = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese día");
        di = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese año de límite de Reporte");
        af = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese mes");
        mf = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese día");
        df = sc.nextInt();
        sc.nextLine();
        Date inicio = new Date(ai - 1900, mi, di);
        Date fin = new Date(af - 1900, mf, df);
        for (int i = 0; i < Ventas.size(); i++) {
            v = Ventas.get(i);
            if (v.getFechaCompra().after(inicio) && v.getFechaCompra().before(fin)) {
                v.generarFactura();
                System.out.println(
                        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }
    }

    public void balance() {
        int ai, mi, di, af, mf, df;
        Venta v;
        float total = 0;
        System.out.println("Ingrese año de inicio de Reporte");
        ai = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese mes");
        mi = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese día");
        di = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese año de límite de Reporte");
        af = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese mes");
        mf = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese día");
        df = sc.nextInt();
        sc.nextLine();
        Date inicio = new Date(ai - 1900, mi, di);
        Date fin = new Date(af - 1900, mf, df);
        for (int i = 0; i < Ventas.size(); i++) {
            v = Ventas.get(i);
            if (v.getFechaCompra().after(inicio) && v.getFechaCompra().before(fin)) {
                total = total + v.getTotal();
            }
        }
        System.out.println(
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Balance: $" + total);
        System.out.println("Concepto de IVA: $" + total * 0.16);
        System.out.println("Ingresos Reales: $" + total * 0.84);
    }

    public void balanceInventario() {
        float inversion = 0, ganancia = 0;
        for (Producto p : Inventario) {
            inversion += (p.getCantidad() * p.getPrecioCompra());
            ganancia += (p.getCantidad() * p.getPrecioVenta());
        }
        System.out.println(
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Costo de Inventario: $" + inversion);
        System.out.println("Ganancia Bruta: $" + ganancia);
        System.out.println("Ganancia Total: $" + (ganancia - inversion));
    }

    public static void main(String[] args) {
        Tiendita tiendita = new Tiendita();
        System.out.println(
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Tiendita ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        int sel = 0, i;
        while (sel != 10) {
            System.out.println(
                    "Actividades:\n1) Agregar Producto\n2) Modificar Producto\n3) Eliminar Producto\n4) Mostrar Inventario\n5) Realizar Venta\n6) Mostrar Ventas\n7) Generar Reporte\n8) Balance de Ingresos\n9) Balance de Inversión en Inventario\n10) Salir");
            sel = sc.nextInt();
            sc.nextLine();
            switch (sel) {
            case 1: // Agregar Producto
                tiendita.agregarProducto();
                System.out.println("Elemento agregado satisfactoriamente");
                break;
            case 2: // Modificar Producto
                System.out.println("Ingrese el id del producto deseado: ");
                i = sc.nextInt();
                sc.nextLine();
                tiendita.modificarProducto(i);
                break;
            case 3: // Eliminar Producto
                System.out.println("Ingrese el id del producto deseado: ");
                i = sc.nextInt();
                sc.nextLine();
                tiendita.eliminarProducto(i);
                break;
            case 4: // Mostrar Inventario
                tiendita.mostrarInventario();
                break;
            case 5: // Realizar Venta
                tiendita.realizarVenta();
                break;
            case 6: // Mostrar Ventas
                tiendita.mostrarVentas();
                break;
            case 7: // Generar Reporte
                tiendita.generarReporte();
                break;
            case 8: // Generar Balance
                tiendita.balance();
                break;
            case 9: // Generar Balance de Inventario
                tiendita.balanceInventario();
                break;
            case 10: // Salir
                tiendita.guardado();
                break;
            default: // Ninguna
                System.out.println("Intente de nuevo c:");
                break;
            }
        }
    }
}