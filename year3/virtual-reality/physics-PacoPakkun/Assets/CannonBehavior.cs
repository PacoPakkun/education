using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CannonBehavior : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
    }
    
    public Rigidbody projectile;

    // Update is called once per frame
    void Update()
    {
        Vector3 mouse = Input.mousePosition;
        Vector3 mouseWorld = Camera.main.ScreenToWorldPoint(new Vector3(
            mouse.x,
            mouse.y,
            transform.position.y + 20));
        transform.LookAt(mouseWorld);

        if (Input.GetMouseButtonDown(0))
        {
            Rigidbody clone;
            clone = Instantiate(projectile, transform.position, transform.rotation);
            clone.velocity = transform.TransformDirection(Vector3.forward * 20);
        }
    }
}